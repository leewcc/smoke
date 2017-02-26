package com.smoke.web;

import com.smoke.entity.Equipment;
import com.smoke.entity.Phone;
import com.smoke.entity.Room;
import com.smoke.entity.Teacher;
import com.smoke.smoke.SharedObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

/**
 * Created by asus2015 on 2016/12/8.
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String getIndex() {
        return "/WEB-INF/dist/index.html";
    }

    @RequestMapping("/history")
    public String getHistory() {
        return "/WEB-INF/dist/index.html";
    }
    @RequestMapping("/room")
    public String getRoom() {
        return "/WEB-INF/dist/index.html";
    }

    @RequestMapping("/device")
    public String getDevice() {
        return "/WEB-INF/dist/index.html";
    }

    @RequestMapping("/login")
    public String getLogin() {
        return "/WEB-INF/dist/index.html";
    }

    @RequestMapping("/manage")
    public String getManage() {
        return "/WEB-INF/dist/index.html";
    }
    @RequestMapping("/updata")
    public String getUpdata() {
        return "/WEB-INF/dist/index.html";
    }

    @RequestMapping("/hhh")
    public String getHhh() {
        return "hhh";
    }

    @RequestMapping("/testInput")
    public String input() {
        // 遍历老师
        Set<Integer> ids = SharedObject.teachers.keySet();

        for(int id :ids) {
            Teacher teacher = SharedObject.teachers.get((Integer)id);
            System.out.format("老师id : %d  老师名： %s \n", teacher.getUserId(), teacher.getUserName());

            List<Phone> phones = teacher.getPhones();
            System.out.print("老师的手机号码：");
            for(Phone phone : phones) {
                System.out.println(phone.getUserPhone() + "  ");
            }
            System.out.println();

            List<Room> rooms = teacher.getRooms();
            System.out.println("老师的房间:");
            for(Room room : rooms) {
                System.out.format("房间号：%d   房间名：%s\n", room.getRoomId(), room.getRoomName());

                List<Equipment> equipments = room.getEquipments();
                for(Equipment equipment : equipments) {
                    System.out.format("设备号：%s   设备名：%s\n", equipment.getEquipmentId(), equipment.getEquipmentName());
                }
            }

            System.out.println();
        }

        return "hhh";
    }
}
