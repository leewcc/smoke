package com.smoke.smoke;

import com.smoke.entity.Equipment;
import com.smoke.entity.Room;
import com.smoke.entity.Teacher;

import java.util.Map;

/**
 * @TODO 服务器初始化时，需要提前从数据库初始化所有信息，将其缓存起来
 * Created by asus2015 on 2016/11/30.
 */
public class SharedObject {
    public static Map<Integer, Teacher> teachers;       // 用户id - 用户

    public static Map<Integer, Teacher> roomToTeacher;  // 房间id - 用户

    public static Map<Integer, Room> rooms;             // 房间号 - 房间

    public static Map<String, Equipment> equipments;   //　设备号 - 设备

}
