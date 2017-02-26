package com.smoke.service;

import com.smoke.dao.RoomDao;
import com.smoke.entity.Equipment;
import com.smoke.entity.Room;
import com.smoke.entity.SmokeStatus;
import com.smoke.entity.Teacher;
import com.smoke.smoke.SharedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHEN on 2016/12/1.
 */
@Service
public class RoomService {

    @Autowired
    private RoomDao roomDao;

    public boolean addRoom(Room room) {
        boolean result = roomDao.addRoom(room);

        if (result) {
            room.setEquipments(new ArrayList<Equipment>());
            room.setHistorys(new ArrayList<SmokeStatus>());
            room.setWarningEquipment(new ArrayList<String>());

            // 获取用户的信息
            Teacher teacher = SharedObject.teachers.get((Integer) room.getUserId());
            if (teacher == null) {
                ;
            } else {
                List<Room> rooms = teacher.getRooms();
                synchronized (rooms) {
                    rooms.add(room);
                }

                synchronized (SharedObject.rooms) {
                    SharedObject.rooms.put(room.getRoomId(), room);
                }

                synchronized (SharedObject.roomToTeacher) {
                    SharedObject.roomToTeacher.put(room.getRoomId(), teacher);
                }
            }
        }

        return result;
    }

    public boolean deleteRoom(int roomId) {

        boolean result = roomDao.deleteRoom(roomId);
        try {
            if (result) {
                synchronized (SharedObject.rooms) {
                    SharedObject.rooms.remove((Integer) roomId);
                }

                // 获取老师
                Teacher teacher = SharedObject.roomToTeacher.get((Integer) roomId);
                Room room = new Room();
                room.setRoomId(roomId);
                synchronized (teacher) {
                    teacher.getRooms().remove(room);
                }

                synchronized (SharedObject.roomToTeacher) {
                    SharedObject.roomToTeacher.remove((Integer) roomId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateRoom(Room room) {
        boolean result = roomDao.updateRoom(room);

        if (result) {
            Room r = SharedObject.rooms.get(room.getRoomId());

            if (r != null) {
                synchronized (r) {
                    r.setRoomName(room.getRoomName());
                }
            }
        }

        return result;
    }

    public List<Room> queryByTeacherId(int userId) {
        return roomDao.queryByTeacherId(userId);
    }
}
