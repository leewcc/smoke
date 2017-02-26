package com.smoke.listener;


import com.smoke.dao.HistoryDao;
import com.smoke.entity.*;
import com.smoke.service.*;
import com.smoke.smoke.SharedObject;

import com.smoke.smoke.server_2.SmokeDataHandler;
import com.smoke.smoke.server_2.SmokeDataMonitor;
import com.smoke.websocket.SmokeWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.ServletContextEvent;

import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.smoke.smoke.SharedObject.equipments;
import static com.smoke.smoke.SharedObject.rooms;

/**
 * Created by asus2015 on 2016/11/30.
 */
@WebListener()
public class ProjectInitializationListener extends ContextLoaderListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectInitializationListener.class);

//    private TeacherDao teacherDao;
//
//    private RoomDao roomDao;
//
//    private EquipmentDao equipmentDao;
//
//    private HistoryDao historyDao;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private HistoryService historyService;

    private PhoneService phoneService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
//
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());

            teacherService = webApplicationContext.getBean(TeacherService.class);

            roomService = webApplicationContext.getBean(RoomService.class);

            equipmentService = webApplicationContext.getBean(EquipmentService.class);

            historyService = webApplicationContext.getBean(HistoryService.class);

            phoneService = webApplicationContext.getBean(PhoneService.class);

            HistoryDao historyDao = webApplicationContext.getBean(HistoryDao.class);

            LOGGER.info("开始初始化The project is initizling");


            // 初始化 mybatis 文件
//            String resource = "mybatis/mybatis-config.xml";
//            Reader reader = null;
//            try {
//                reader = Resources.getResourceAsReader(resource);
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//
//            }
//            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//            SqlSession sqlSession = sqlSessionFactory.openSession();
//            teacherDao = sqlSession.getMapper(TeacherDao.class);
//            roomDao = sqlSession.getMapper(RoomDao.class);
//            equipmentDao = sqlSession.getMapper(EquipmentDao.class);
//            historyDao = sqlSession.getMapper(HistoryDao.class);

            // 初始化共享内存对象
            initSharedObject();

            // 初始化 websocket 用户连接对象
            initWebSocket();

            // 获取所有用户
            List<Teacher> teachers = teacherService.getAllTeacher();

            // 遍历所有用户，获取所有用户的房间的设备和历史记录
            for (Teacher teacher : teachers) {
                LOGGER.debug("老师名：" + teacher.getUserName());

                // 将用户放进共享内存对象中
                SharedObject.teachers.put(teacher.getUserId(), teacher);

                // 初始化用户关联的 websocket 连接集合
                List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList());
                SmokeWebSocketHandler.map.put(teacher.getUserId(), sessions);

                // 获取用户电话
                List<Phone> phones = phoneService.queryByUserId(teacher.getUserId());
                teacher.setPhones(phones);

                // 遍历用户房间
                List<Room> rooms = roomService.queryByTeacherId(teacher.getUserId());
                teacher.setRooms(rooms);
//                List<Room> rooms = teacher.getRooms();
                for (Room room : rooms) {
                    // 将房间放进共享对象中
                    SharedObject.roomToTeacher.put(room.getRoomId(), teacher);
                    SharedObject.rooms.put(room.getRoomId(), room);

                    LOGGER.debug("房间号： " + room.getRoomId() + "房间名：" + room.getRoomName());

                    // 创建记录警告设备的集合
                    room.setWarningEquipment(Collections.synchronizedList(new ArrayList()));

                    // 获取房间的设备和历史记录
                    List<Equipment> equipments = equipmentService.queryByRoomId(room.getRoomId());
                    List<SmokeStatus> historys = historyService.getHistoryByRoomId(room.getRoomId());
                    room.setWarningEquipment(new ArrayList<String>());
                    room.setEquipments(equipments);
                    room.setHistorys(historys);

                    // 将房间号放到共享内存对象中
                    for (Equipment equipment : equipments) {
                        SharedObject.equipments.put(equipment.getEquipmentId(), equipment);
                    }
                }

            }

            LOGGER.info("starting connect server and wait datagram arrive ");
            // 初始化数据监听器，开启线程去监听烟雾器的数据
            String host = "127.0.0.1";
            int port = 5555;
            Thread smokeDataMonitor = new Thread(new SmokeDataMonitor(port));
            SmokeDataHandler.historyDao = historyDao;
//            smokeDataMonitor.setDaemon(true);
            smokeDataMonitor.start();
//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private void initSharedObject() {
        SharedObject.teachers = new ConcurrentHashMap<>();
        SharedObject.roomToTeacher = new ConcurrentHashMap<>();
        SharedObject.rooms = new ConcurrentHashMap<>();
        SharedObject.equipments = new ConcurrentHashMap<>();
    }

    private void initWebSocket() {
        SmokeWebSocketHandler.map = new ConcurrentHashMap<>();
    }
}
