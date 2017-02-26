package com.smoke.smoke.Server;

import com.smoke.dao.*;
import com.smoke.entity.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by asus2015 on 2016/12/3.
 */
public class ClientHandler {
    private Socket socket;
    private static List<Teacher> teachers = new ArrayList<>();
    static {
        String resource = "mybatis/mybatis-config.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TeacherDao teacherDao = sqlSession.getMapper(TeacherDao.class);
        RoomDao roomDao = sqlSession.getMapper(RoomDao.class);
        EquipmentDao equipmentDao = sqlSession.getMapper(EquipmentDao.class);
        HistoryDao historyDao = sqlSession.getMapper(HistoryDao.class);
        PhoneDao phoneDao = sqlSession.getMapper(PhoneDao.class);

        teachers = teacherDao.getAllTeacher();
        for(Teacher teacher : teachers) {
            List<Room> rooms = roomDao.queryByTeacherId(teacher.getUserId());
            teacher.setRooms(rooms);

            List<Phone> phones = phoneDao.queryByUserId(teacher.getUserId());
            teacher.setPhones(phones);

            for(Room room : rooms) {
                room.setEquipments(equipmentDao.queryByRoomId(room.getRoomId()));
            }
        }
    }

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public synchronized void handle() throws IOException, InterruptedException {
        BufferedOutputStream buffer = new BufferedOutputStream(socket.getOutputStream());

        ByteBuffer byteBuffer = ByteBuffer.allocate(900);
        wait(5000);
        int i = 1;
        int teacherNum = teachers.size();
        while(true) {
            if(i % 4 == 0) {
                int random = (int)(Math.random() * teacherNum);
                Teacher teacher = teachers.get(random);
                int roomNum = teacher.getRooms().size();
                random = (int)(Math.random() * roomNum);
                Room room = teacher.getRooms().get(random);
                int equipmentNum = room.getEquipments().size();
                random = (int)(Math.random() * equipmentNum);
                Equipment equipment = room.getEquipments().get(random);
                byteBuffer.put(DatagramBuilder.createDatagram(room.getRoomId(), 1,1));

            } else {
                for(Teacher teacher : teachers) {
                    List<Room> rooms = teacher.getRooms();
                    for(Room room : rooms) {
                        List<Equipment> equipments = room.getEquipments();
                        for(Equipment equipment : equipments) {
                            byteBuffer.put(DatagramBuilder.createDatagram(room.getRoomId(), 1,1));
                        }
                    }
                }
            }


            byteBuffer.flip();
            System.out.println("开始发送数据，数据长度是" + byteBuffer.remaining() + " 头部位置是" + byteBuffer.position() + " 尾部位置:" + byteBuffer.limit());
            byte[] data = new byte[byteBuffer.remaining()];
            byteBuffer.get(data);
            buffer.write(data);
            byteBuffer.clear();
            buffer.flush();
            wait(10000);
            i++;
        }
    }
}
