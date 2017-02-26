package com.smoke.dao;

import com.smoke.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CHEN on 2016/11/26.
 */
@Repository
public interface TeacherDao {

    /**
     * 添加一个老师
     * @param teacher
     * @return
     */
    boolean addTeacher(Teacher teacher);

    /**
     * 根据老师的id更新一个老师的信息
     * @param teacher
     * @return
     */
    boolean updateTeacher(Teacher teacher);

    /**
     * 获得所有的老师集合
     * @return
     */
    List<Teacher> getAllTeacher();

    /**
     * 获取所有老师，包括老师的房间和手机
     * @return
     */
    List<Teacher> getTeachers();

    /**
     *
     * @return
     */
    List<Teacher> getTeachersAndPhone();

}
