package com.smoke.entity;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.util.List;

/**
 * Created by CHEN on 2016/11/26.
 */
@JsonSerialize(using =EquipmentSerializer.class)
public class Equipment {

    private String equipmentId;             //设备编号

    private int id;                          //业务id

    private String equipmentName;        //设备名字
    private int roomId;
    private int status;


    //实时数据
    private SmokeStatus currentStatus;   //当前烟雾状态

    private Room room;                     //绑定的房间

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public SmokeStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(SmokeStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;

        if(obj instanceof Equipment) {
            Equipment phone = (Equipment)obj;
            if(phone.getId() == this.getId()) {
                return true;
            }
        }

        return false;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

class EquipmentSerializer extends JsonSerializer<Equipment> {

    @Override
    public void serialize(Equipment value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("equipmentId", value.getEquipmentId());
        jgen.writeNumberField("roomId", value.getRoomId());
        jgen.writeStringField("equipmentName", value.getEquipmentName());

       SmokeStatus smokeStatus = value.getCurrentStatus();
        if(smokeStatus == null) {
            smokeStatus = new com.smoke.entity.SmokeStatus();
        }

        jgen.writeNumberField("status", value.getStatus());
        jgen.writeNumberField("pm1_0", smokeStatus.getPm1_0());
        jgen.writeNumberField("pm2_5", smokeStatus.getPm2_5());
        jgen.writeNumberField("pm10", smokeStatus.getPm10());
        jgen.writeNumberField("recordTime", smokeStatus.getRecordTime());
        jgen.writeEndObject();
    }
}