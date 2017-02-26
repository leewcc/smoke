package com.smoke.web;

import com.smoke.dto.DataGram;
import com.smoke.dto.Message;
import com.smoke.entity.Equipment;
import com.smoke.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sun.xml.internal.ws.api.message.Packet.Status.Response;

/**
 * Created by CHEN on 2016/12/1.
 */
@Controller
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    /**
     * 添加设备
     * @param equipment
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/smoke/equipment",method= RequestMethod.POST)
    public DataGram<Equipment> addEquipment(@RequestBody Equipment equipment) {
        Message message;
        if(equipmentService.addEquipment(equipment)) {
           return new DataGram<>(equipment,true);
        } else {
            return new DataGram<>(null,false);
        }
    }

    /**
     * 修改设备
     * 先判断这个设备是否属于这个用户
     *
     * @param equipment
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/smoke/equipment",method= RequestMethod.PUT)
    public Message updateEquipment(@RequestBody Equipment equipment) {
        Message message;
        if(equipmentService.updateEquipment(equipment)) {
            message=new Message(1,"修改设成功");
        } else {
            message=new Message(0,"修改失败，可能是因为该设备号已经存在");
        }
        return message;
    }

    /**
     * 删除设备
     * 先判断这个设备是否属于这个用户
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/smoke/equipment/{id}",method= RequestMethod.DELETE)
    public Message deleteEquipment(@PathVariable("id") int id) {
        Message message;
        if(equipmentService.deleteEquipment(id)) {
            message=new Message(1,"删除设备成功");
        } else {
            message=new Message(0,"删除设备失败");
        }
        return message;
    }

    /**
     * 根据房间号查询所有的设备
     * 首先进行一个判断
     * 这个房间是否属于这个用户
     * 然后展示设备
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/room/{roomId}/equipmentlist")
    public DataGram<List<Equipment>> queryByRoomId(@PathVariable("roomId")int roomId) {
        DataGram<List<Equipment>> data=new DataGram<>();
        data.setData(equipmentService.queryByRoomId(roomId));
        if(data.getData()!=null) {
            data.setSuccess(true);
        } else {
            data.setSuccess(false);
        }
        return data;
    }

}
