package com.smoke.web;

import com.smoke.dto.DataGram;
import com.smoke.dto.Message;
import com.smoke.entity.Teacher;
import com.smoke.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import java.util.List;

import static com.sun.xml.internal.ws.api.message.Packet.Status.Response;
import static javafx.scene.input.KeyCode.R;

/**
 * Created by CHEN on 2016/12/1.
 * <p>
 * <p>
 * 保存老师的接口
 */
@Controller

public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    /**
     * 获得所有的老师
     *
     * @return DataGram 包含了老师列表的数据报
     */
    @RequestMapping(value = "/teacher/list", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public DataGram<List<Teacher>> getAllTeacher() {
        DataGram<List<Teacher>> data = new DataGram<>();

        List<Teacher> teachers = teacherService.getAllTeacher();
        if (teachers == null) {
            data.setSuccess(false);
        } else {
            data.setData(teachers);
            data.setSuccess(true);
        }
        return data;
    }


    /**
     * 增加一个老师
     *
     * @param teacher
     * @return
     */
    @RequestMapping(value = "/smoke/teacher", method = RequestMethod.POST)
    @ResponseBody
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        System.out.println("添加");
        Message message;
        if (teacherService.addTeacher(teacher)) {
            message = new Message(1, "添加老师成功");
        } else {
            message = new Message(0, "添加老师失败");
        }
        return teacher;

    }

    @RequestMapping(value = "/smoke/teacher/{teacherId}",method=RequestMethod.DELETE)
    @ResponseBody
    public Message deleteTeacher(@PathVariable("teacherId") int teacherId) {
        Message message;
        if (teacherService.deleteTeacher(teacherId)) {
            message = new Message(1, "删除老师成功");
        } else {
            message = new Message(0, "删除老师失败");
        }
        return message;
    }

    @RequestMapping(value="/smoke/teacher",method=RequestMethod.PUT)
    @ResponseBody
    public Message updateTeacher(@RequestBody Teacher teacher) {
        Message message;
        if (teacherService.updateTeacher(teacher)) {
            message = new Message(1, "修改信息成功");
        } else {
            message = new Message(0, "修改信息失败");
        }
        return message;
    }

    @RequestMapping(value="/smoke/teacher",method=RequestMethod.GET)
    public String showTeacher() {
        return "/WEB-INF/dist/index.html";//老师信息的页面
    }



    @ResponseBody
    @RequestMapping(value="/smoke/teachersInfo",method = RequestMethod.GET)
    public List<Teacher> getTeachersAndPhone() {
        List<Teacher> teachers=teacherService.getTeachersAndPhone();
        return teachers;
    }

    @ResponseBody
    @RequestMapping(value="/smoke/teacher/{teacherId}/room",method=RequestMethod.GET)
    public Teacher getTeacherAndRoom(@PathVariable("teacherId") int teacherId) {
        Teacher t;
        t=teacherService.getTeacherAndRoom(teacherId);
        return t;
    }

}
