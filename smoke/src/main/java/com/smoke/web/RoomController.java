package com.smoke.web;

import com.smoke.dto.DataGram;
import com.smoke.dto.Message;
import com.smoke.entity.Room;
import com.smoke.service.RoomService;
import com.smoke.service.RoomService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by CHEN on 2016/12/1.
 */
@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     *
     * @param room
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/smoke/room",method= RequestMethod.POST)
    public DataGram<Room> addRoom(@RequestBody Room room) {
        DataGram<Room> data=null;
        if(roomService.addRoom(room)) {
            data=new DataGram<Room>(room,true);
        } else {
            data=new DataGram<Room>(null,false);
        }
        return data;
    }

    /**
     *
     * @param roomId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/smoke/room/{roomId}",method= RequestMethod.DELETE)
    public Message deleteRoom(@PathVariable("roomId")int roomId) {
        Message message;
        if(roomService.deleteRoom(roomId)) {
            message=new Message(1,"删除房间成功");
        } else {
            message=new Message(0,"删除房间失败");
        }
        return message;
    }

    /**
     *
     * @param room
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/smoke/room",method= RequestMethod.PUT)
    public Message updateRoom(@RequestBody Room room) {
        Message message;
        if(roomService.updateRoom(room)) {
            message=new Message(1,"修改房间成功");
        } else {
            message=new Message(0,"修改房间失败，可能是该房间号已经存在");
        }
        return message;
    }

    /**
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/user/{userId}/roomlist",method = RequestMethod.GET)
    public DataGram<List<Room>> queryByUserId(@PathVariable("userId") int userId) {
        DataGram<List<Room>> data=new DataGram<>();
        data.setData(roomService.queryByTeacherId(userId));
        if(data.getData()!=null) {
            data.setSuccess(true);
        } else {
            data.setSuccess(false);
        }
        return data;
    }

}
