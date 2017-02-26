package com.smoke.smoke.server_2;

import com.smoke.api.Message;
import com.smoke.dao.HistoryDao;
import com.smoke.entity.*;
import com.smoke.smoke.SharedObject;
import com.smoke.utils.JacksonUtil;
import com.smoke.utils.StringUtil;
import com.smoke.websocket.SmokeWebSocketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.smoke.smoke.SharedObject.teachers;

/**
 * Created by asus2015 on 2016/12/12.
 */
public class SmokeDataHandler extends ChannelHandlerAdapter {
    private static Logger LOGGER = LoggerFactory.getLogger(SmokeDataHandler.class);

    public static HistoryDao historyDao;

    static {

    }

    // 用来存放此次报文中有哪些用户信息的设备,用来存放有多少个状态报文是属于对应用户的
    List<SmokeStatus> smokeStatusList = null;
    Map<Integer, List<SmokeStatus>> userSmokestatus = new HashMap<>();
    Map<Teacher, List<Room>> userRoomWarning = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) throws UnsupportedEncodingException {
        // 读取烟雾器传来的烟雾状态
        ByteBuf buf = (ByteBuf) object;
        int available = buf.readableBytes();

        byte[] datas = new byte[buf.readableBytes()];
        buf.readBytes(datas);
        LOGGER.debug("此处发送过来的可用数据有 {}", available);

        smokeStatusList = new ArrayList<>();

        try {
            // 计算此次数据报中有多少个状态报文
            int number = available / 18;

            // 将字节数组解析成状态报文
            int pos = 0;
            for (int i = 0; i < number; i++) {
                pos = i * 18;
                SmokeStatus smokeStatus = new SmokeStatus(Arrays.copyOfRange(datas, pos, pos + 18));
                LOGGER.debug(smokeStatus.toString());
                add(smokeStatus);
            }

            // 推送信息给前台连接的用户
            doSend();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(SmokeStatus smokeStatus) throws Exception {
        // 获取设备号
        Equipment equipment = SharedObject.equipments.get(smokeStatus.getEquipmentId());

        // 判断设备是否存在
        if (equipment == null) {
            LOGGER.info("组号：{}设备：{} 不存在", smokeStatus.getGroupId(), smokeStatus.getEquipmentId());
            return;
        }

        // 获取该烟雾状态报文所属的房间号
        int roomId = equipment.getRoomId();
        smokeStatus.setRoomId(roomId);

        // 根据房间号获取老师
        Teacher teacher = SharedObject.roomToTeacher.get((Integer) roomId);

        // 若老师不存在，则忽略此条报文
        if (teacher == null) {
            LOGGER.info("房间号：{} 设备：{} 不存在对应的负责人", smokeStatus.getRoomId(), smokeStatus.getEquipmentId());
            return;
        }

        // 若老师存在，则将其加入到老师的报文集合中去
        // 这里需要做同步操作，防止同一个用户的设备并发访问时，导致创建了多个对象
        List<SmokeStatus> smokeStatuses = null;
        synchronized (userSmokestatus) {
            smokeStatuses = userSmokestatus.get((Integer) (teacher.getUserId()));
            if (smokeStatuses == null) {
                smokeStatuses = new ArrayList<>();
                userSmokestatus.put(teacher.getUserId(), smokeStatuses);
            }
        }

        Room room = SharedObject.rooms.get((Integer) (smokeStatus.getRoomId()));


        if (room == null || equipment == null) {
            LOGGER.info("不存在该房间：{} 或该房间不存在该设备：{}", smokeStatus.getRoomId(), smokeStatus.getEquipmentId());
            return;
        }


        // 如果当前报文是警告报文，则改变对应房间的状态
        if (smokeStatus.getstatus() == 1) {
            room.setStatus(1);
            equipment.setStatus(1);
            List<SmokeStatus> historys = room.getHistorys();
            synchronized (historys) {
                historys.add(smokeStatus);
            }

            // 插入一条记录到房间的警告设备中
            List<String> warningEquipments = room.getWarningEquipment();
            synchronized (warningEquipments) {
                if (!warningEquipments.contains(smokeStatus.getEquipmentId())) {
                    warningEquipments.add(smokeStatus.getEquipmentId());

                    // 将报文历史保存到数据库
                    historyDao.insertHistory(smokeStatus);
                }
            }

            // 向需报警用户队列插入一条报警信息
            LOGGER.debug("房间 {}报警了", room.getRoomId());
            List<Room> roomWarning = userRoomWarning.get(teacher);
            synchronized (userRoomWarning) {
                if (roomWarning == null) {
                    roomWarning = new ArrayList<>();
                    userRoomWarning.put(teacher, roomWarning);
                }
            }
            roomWarning.add(room);



        } else {
            // 判断该设备之前是否报警，若是，则取消它的报警状态
            List<String> warningEquipment = room.getWarningEquipment();
            synchronized (warningEquipment) {
                if (warningEquipment.contains(smokeStatus.getEquipmentId())) {
                    warningEquipment.remove(smokeStatus.getEquipmentId());

                    // 判断房间是否没有警告设备，若没，则将房间状态设置为正常
                    if (warningEquipment.size() <= 0) {
                        room.setStatus(0);
                    }
                }
            }
            equipment.setStatus(0);
            room.setHasSendMess(false);
        }

        synchronized (equipment) {
            equipment.setCurrentStatus(smokeStatus);
        }

        smokeStatuses.add(smokeStatus);
    }

    public void doSend() throws Exception {
        // 获取报文的用户集
        Set<Integer> users = userSmokestatus.keySet();

        for (Integer user : users) {
            List<SmokeStatus> smokeStatuses = userSmokestatus.get(user);
            SmokeWebSocketHandler.sendMessage(user, JacksonUtil.jsonToMap(new Object[]{"choice", "teaId", "smokeStatus"}, new Object[]{4, user, smokeStatuses}));
            smokeStatuses.clear();
        }

        // 发送报警信息
        notifyUser();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    private void notifyUser() {
        Set<Teacher> teachers = userRoomWarning.keySet();
        for (Teacher teacher : teachers) {
            List<Room> warningRooms = userRoomWarning.get(teacher);
            List<Phone> phones = teacher.getPhones();
            String username = teacher.getUserName();
            for (Room room : warningRooms) {
                if (!room.isHasSendMess()) {
                    for (Phone phone : phones) {
                        Message.sendMessage(phone.getUserPhone(), username, room.getRoomName());
                    }
                    room.setHasSendMess(true);
                }
            }
            warningRooms.clear();
        }
    }


}
