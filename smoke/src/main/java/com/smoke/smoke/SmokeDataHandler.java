//package com.smoke.smoke;
//
//import com.smoke.entity.Equipment;
//import com.smoke.entity.Room;
//import com.smoke.entity.SmokeStatus;
//import com.smoke.entity.Teacher;
//import com.smoke.utils.JacksonUtil;
//import com.smoke.websocket.SmokeWebSocketHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//import java.io.IOException;
//import java.util.*;
//
///**
// * 该类用于解析并处理数据报文
// */
//public class SmokeDataHandler implements Runnable{
//    private static Logger LOGGER = LoggerFactory.getLogger(SmokeDataHandler.class);
//
//    // 用来存放此次报文中有哪些用户信息的设备,用来存放有多少个状态报文是属于对应用户的
//    Map<Integer, List<SmokeStatus>> userSmokestatus = new HashMap<>();
//    Map<Integer, List<Room>> userRoomWarning = new HashMap<>();
//
//    private byte[] datas;
//    private int length;
//
//    public SmokeDataHandler(byte[] datas, int length) {
//        this.datas = datas;
//        this.length = length;
//    }
//
//
//    @Override
//    public void run() {
//        try {
//            // 计算此次数据报中有多少个状态报文
//            int number = length / 18;
//
//            // 将字节数组解析成状态报文
//            int pos = 0;
//            for (int i = 0; i < number; i++) {
//                pos = i * 18;
//                SmokeStatus smokeStatus = new SmokeStatus(Arrays.copyOfRange(datas, pos, pos + 18));
//                LOGGER.debug(smokeStatus.toString());
//                add(smokeStatus);
//            }
//
//            // 推送信息给前台连接的用户
//            doSend();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void add(SmokeStatus smokeStatus) throws Exception{
//        // 获取该烟雾状态报文所属的房间号
//        int roomId = smokeStatus.getRoomId();
//
//        // 根据房间号获取老师
//        Teacher teacher = SharedObject.roomToTeacher.get(roomId);
//
//        // 若老师不存在，则忽略此条报文
//        if(teacher == null) {
//            return;
//        }
//
//        // 若老师存在，则将其加入到老师的报文集合中去
//        List<SmokeStatus> smokeStatuses = userSmokestatus.get(teacher.getUserId());
//        if(smokeStatuses == null) {
//            smokeStatuses = new ArrayList<>();
//            userSmokestatus.put(teacher.getUserId(), smokeStatuses);
//        }
//
//        Room room = SharedObject.rooms.get(smokeStatus.getRoomId());
//        Equipment equipment = SharedObject.equipments.get(smokeStatus.getEquipmentId());
//
//        // 如果当前报文是警告报文，则改变对应房间的状态
//        if(smokeStatus.getstatus() == 1) {
//            room.setStatus(1);
//            room.getHistorys().add(smokeStatus);
//
//            // 插入一条记录到房间的警告设备中
//            room.getWarningEquipment().add(smokeStatus.getEquipmentId());
//
//            // 向需报警用户队列插入一条报警信息
//            List<Room> warningRooms = userRoomWarning.get(teacher.getUserId());
//            if(warningRooms == null) {
//                warningRooms = new ArrayList<>();
//                userRoomWarning.put(teacher.getUserId(), warningRooms);
//            }
//            warningRooms.add(room);
//
//            // 将报文历史保存到数据库,此处未填写
//
//        }else{
//            // 判断该设备之前是否报警，若是，则取消它的报警状态
//            List<Integer> warningEquipment = room.getWarningEquipment();
//            if(warningEquipment.contains(smokeStatus.getEquipmentId())) {
//                warningEquipment.remove((Integer)smokeStatus.getEquipmentId());
//
//                // 判断房间是否没有警告设备，若没，则将房间状态设置为正常
//                if(warningEquipment.size() <= 0) {
//                    room.setStatus(0);
//                }
//            }
//
//        }
//
//        equipment.setCurrentStatus(smokeStatus);
//
//        smokeStatuses.add(smokeStatus);
//    }
//
//    public void doSend() throws Exception{
//        // 获取报文的用户集
//        Set<Integer> users = userSmokestatus.keySet();
//
//        for(Integer user : users) {
//            SmokeWebSocketHandler.sendMessage(user, JacksonUtil.jsonToMap(new Object[]{"choice","teaId","smokeStatus"}, new Object[]{4,user,userSmokestatus.get(user)}));
//        }
//
//        // 发送报警信息
//
//    }
//}
