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
@JsonSerialize(using =TeacherSerializer.class)
public class Teacher extends User{

    private List<Room> rooms;
    private List<Phone> phones;

    public Teacher() {
    }

    public Teacher(int userId,String userName) {
        this.setUserId(userId);
        this.setUserName(userName);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}

class TeacherSerializer extends JsonSerializer<Teacher> {

    @Override
    public void serialize(Teacher value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("userId", value.getUserId());
        jgen.writeStringField("userName", value.getUserName());
        jgen.writeEndObject();
    }
}