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
@JsonSerialize(using =RoomSerializer.class)
public class Room {

    private int roomId;                   //房间编号
    private String roomName;              //房间名字
    private int status;                   // 房间状态

    private int userId;
    private Teacher teacher;              //房间的管理者

    private List<Equipment> equipments;   //设备

    private List<SmokeStatus> historys;   // 该房间的历史警告信息

    private List<String> warningEquipment;

    private boolean hasSendMess;    // 是否已经发过短信

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<SmokeStatus> getHistorys() {
        return historys;
    }

    public void setHistorys(List<SmokeStatus> historys) {
        this.historys = historys;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getWarningEquipment() {
        return warningEquipment;
    }

    public void setWarningEquipment(List<String> warningEquipment) {
        this.warningEquipment = warningEquipment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isHasSendMess() {
        return hasSendMess;
    }

    public void setHasSendMess(boolean hasSendMess) {
        this.hasSendMess = hasSendMess;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;

        if(obj instanceof Room) {
            Room phone = (Room) obj;
            if(phone.getRoomId() == this.getRoomId()) {
                return true;
            }
        }

        return false;
    }
}

class RoomSerializer extends JsonSerializer<Room> {


    @Override
    public void serialize(Room value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("roomId", value.getRoomId());
        jgen.writeStringField("roomName", value.getRoomName());
        jgen.writeNumberField("roomStatus", value.getStatus());
        jgen.writeObjectField("warnEqu", value.getWarningEquipment());
        jgen.writeEndObject();
    }
}