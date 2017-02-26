package com.smoke.service;

import com.smoke.dao.EquipmentDao;
import com.smoke.dao.RoomDao;
import com.smoke.dao.TeacherDao;
import com.smoke.dao.UserDao;
import com.smoke.entity.*;
import com.smoke.smoke.SharedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.smoke.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CHEN on 2016/12/1.
 */
@Service
public class TeacherService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private RoomDao roomDao;

    /**
     * 首先在t_user 增加一个用户
     * 然后在t_teacher 增加一个老师的信息
     * @param teacher
     * @return
     */
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public boolean addTeacher(Teacher teacher) {
        userDao.addUser(teacher);
        boolean result = teacherDao.addTeacher(teacher);

        // 同步对象
        if(result) {
            teacher.setRooms(new ArrayList<Room>());
            teacher.setPhones(new ArrayList<Phone>());
            synchronized (SharedObject.teachers) {
                SharedObject.teachers.put(teacher.getUserId(), teacher);
            }
        }

        return true;
    }

    /**
     * 暂时定义为更新用户信息
     * 因为老师表中并没有其他信息
     * @param teacher
     * @return
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public boolean updateTeacher(Teacher teacher) {
        boolean result = userDao.updateUser(teacher);

        if(result) {
            Teacher t = SharedObject.teachers.get((Integer)teacher.getUserId());
            if(t != null) {
                synchronized (t) {
                    t.setUserName(teacher.getUserName());
                }
            }
        }

        return result;
    }

    /**
     * 获得所有的老师信息
     * @return
     */
    @Transactional(readOnly = true)
    public List<Teacher> getAllTeacher() {
        return teacherDao.getAllTeacher();
    }

    /**
     * 删除老师的信息
     * @return
     */
    public boolean deleteTeacher(int userId) {
        boolean result = userDao.deleteUser(userId);

        if(result) {
            synchronized (SharedObject.teachers) {
                SharedObject.teachers.remove((Integer) userId);
            }
        }

        return result;
    }

    public List<Teacher> getTeachers() {
        return teacherDao.getTeachers();
    }

    public List<Teacher> getTeachersAndPhone() {
        return teacherDao.getTeachersAndPhone();
    }

    public Teacher getTeacherAndRoom(int teacherId) {
        Teacher t=new Teacher();
        List<Room> roomList=roomDao.queryByTeacherId(teacherId);
        for(Room r:roomList) {
            List<Equipment> equipmentList=equipmentDao.queryByRoomId(r.getRoomId());
            r.setEquipments(equipmentList);
        }
        t.setRooms(roomList);
        return t;

    }
}
