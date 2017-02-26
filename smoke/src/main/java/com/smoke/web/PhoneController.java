package com.smoke.web;

import com.smoke.dto.DataGram;
import com.smoke.dto.Message;
import com.smoke.entity.Equipment;
import com.smoke.entity.Phone;
import com.smoke.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CHEN on 2016/12/1.
 */
@Controller
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @ResponseBody
    @RequestMapping(value = "/smoke/phone", method = RequestMethod.POST)
    public DataGram<Phone> addPhone(@RequestBody Phone phone) {
        Message message;
        //检查手机号码位数
        Pattern p=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//        Pattern p = Pattern.compile("\\d{11,11}");
        Matcher m = p.matcher(phone.getUserPhone());
        if (!m.matches()) {
            return new DataGram<>(phone,false,0,"电话格式错误");
        } else {
            if (phoneService.addPhone(phone)) {
                return new DataGram<>(phone,true,1,"电话添加成功");
            } else {
                return new DataGram<>(phone,false,0,"电话出现重复");
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/smoke/phone/{id}/{userId}/{userPhone}", method = RequestMethod.DELETE)
    public Message deletePhone(@PathVariable("id")int id ,@PathVariable("userPhone") String userPhone,@PathVariable("userId")int userId) {
        Message message;
        if (phoneService.deletePhone(id,userId,userPhone)) {
            message = new Message(1, "删除电话成功");
        } else {
            message = new Message(0, "删除电话失败");
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/smoke/phone", method = RequestMethod.PUT)
    public Message updatePhone(@RequestBody Phone phone) {
        Pattern p=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//        Pattern p = Pattern.compile("\\d{11,11}");
        Matcher m = p.matcher(phone.getUserPhone());
        if (!m.matches()) {
            return new Message( 0, "电话格式错误");
        }
        Message message;
        if (phoneService.updatePhone(phone)) {
            message = new Message(1, "修改电话成功");
        } else {
            message = new Message(0, "修改电话失败，可能是该号码已经存在");
        }
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/user/{userId}/phonelist", method = RequestMethod.GET)
    public DataGram<List<Phone>> queryByUserId(@PathVariable("userId") int userId) {
        DataGram<List<Phone>> data = new DataGram<>();
        data.setData(phoneService.queryByUserId(userId));
        if (data.getData() != null) {
            data.setSuccess(true);
        } else {
            data.setSuccess(false);
        }
        return data;
    }

}
