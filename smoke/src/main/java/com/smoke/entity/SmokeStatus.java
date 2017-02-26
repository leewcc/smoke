package com.smoke.entity;

import com.smoke.smoke.SharedObject;
import com.smoke.utils.ByteUtil;
import com.smoke.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by asus2015 on 2016/11/29.
 */
public class SmokeStatus {
    private static final byte[] NORMAL = new byte[]{0x4E,0x4f,0x52,0x4d,0x41,0x4c,0x3c,0x3c};
    private static final byte[] WARNING = new byte[]{0x57,0x41,0x52,0x4e,0x49,0x4e,0x47,0x21};

    private int groupId;

    private int roomId;

    private int eId;

    private String equipmentName;

    private String equipmentId;

    private int status;

    private int pm1_0;

    private int pm2_5;

    private int pm10;

    private long recordTime;

    // 数据报，共 18 个字节 1：房间号 2：设备号 3-10：状态信息 11-12：PM1.0  13-14：PM2.5  15-16：PM10 17-18：结束标志
    private byte[] datagram;

    public SmokeStatus() {

    }

    public SmokeStatus(byte[] datagram) {
        this.datagram = datagram;
        parse();
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getstatus() {
        return status;
    }

    public void setstatus(int status) {
        this.status = status;
    }

    public int getPm1_0() {
        return pm1_0;
    }

    public void setPm1_0(int pm1_0) {
        this.pm1_0 = pm1_0;
    }

    public int getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(int pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    private void parse() {
        // 获取房间号
        groupId = (int)datagram[0];

        // 获取设备号
        eId = (int)datagram[1];

        equipmentId = StringUtil.toIdentification(groupId, eId);

        // 获取状态
        status = getStatus(Arrays.copyOfRange(datagram,2,10));

        // 获取 PM1.0 的值
        pm1_0 = ByteUtil.byte2ToInt(Arrays.copyOfRange(datagram,10,12));

        // 获取 PM2.5 的值
        pm2_5 = ByteUtil.byte2ToInt(Arrays.copyOfRange(datagram, 12,14));

        // 获取 PM10 的值
        pm10 = ByteUtil.byte2ToInt(Arrays.copyOfRange(datagram, 14,16));

        // 设置此次报文的时间值
        recordTime = System.currentTimeMillis();
    }

    private int  getStatus(byte[] status) {
        return Arrays.equals(NORMAL,status) ? 0 : 1;
    }

    @Override
    public String toString() {
        return String.format("房间号：%d 设备号：%d  状态：%s  pm1.5：%d  pm2.5：%d  pm10： %d  时间：%d ", roomId,eId,(status == 0 ? "正常" : "警告"),pm1_0,pm2_5,pm10,recordTime );
    }

    public String getEquipmentName() {
        if(equipmentName == null) {
            Equipment equipment = SharedObject.equipments.get(equipmentId);
            if(equipment == null) {
                equipmentName = "";
            } else {
                equipmentName = equipment.getEquipmentName();
            }
        }
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int geteId() {
        return eId;
    }

    public void seteId(int eId) {
        this.eId = eId;
    }
}
