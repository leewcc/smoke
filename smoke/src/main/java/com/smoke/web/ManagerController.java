package com.smoke.web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smoke.dto.Message;
import com.smoke.entity.Manager;
import com.smoke.service.ManagerService;
import com.smoke.utils.MyCookie;
import com.smoke.utils.MyRSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.smoke.websocket.SmokeWebSocketHandler.map;
import static javafx.scene.input.KeyCode.M;

/**
 * Created by CHEN on 2016/11/26.
 */
@Controller
public class ManagerController {

    @Autowired
    private ManagerService managerService;


    /**
     * 获得登录页面
     * @return
     */
    //@RequestMapping(value="/login",method= RequestMethod.GET)
    public String getLoginPage() {
        return "/WEB-INF/dist/html/manager-login.jsp";
    }

    /**
     * 判断用户账号密码
     * @param session
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/login",method= RequestMethod.POST)
//    public Message postLoginPage(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("code") String code, HttpSession session, HttpServletResponse response) {
        public Message postLoginPage(@RequestBody String content, HttpSession session, HttpServletResponse response) {
        //int i=1/0;
        Map<String,String> map= new HashMap<>();
        Gson gson=new Gson();
        map=gson.fromJson(content,new TypeToken<Map<String,String>>(){}.getType());
        Message message=new Message();
        String name=map.get("name");String password=map.get("password");
        String code=map.get("code");
        String sessionCode=(String)session.getAttribute("code");
        //1. 判断用户的验证码
        //验证码 不正确
        if(!sessionCode.equals(code)) {
             message=new Message(0,"验证码错误");
            return message;
        }
        //验证码 正确
        //2. 验证用户
        //解码rsa
        byte[] en_result = new BigInteger(password, 16).toByteArray();
        byte[] de_result=null;
        try {
            de_result = MyRSA.decrypt(MyRSA.getKeyPair().getPrivate(),
                    en_result);
        } catch (Exception e) {
//            model.addAttribute("error","服务器出错");
//            return "manager-login";
            message=new Message(0,"服务器出错");
            return message;
        }
        StringBuffer buffer=new StringBuffer();
        buffer.append(new String(de_result));
        password=buffer.reverse().toString();
        try {
            password= URLDecoder.decode(password,"UTF-8");
            System.out.println(password);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Manager manager= managerService.queryByIdAndPassword(name,password);
        //用户不存在
        if(manager==null) {
            message=new Message(0,"用户名或密码不正确");
//            model.addAttribute("error","用户名或账号不正确");
//            return "manager-login";
            return message;

        }
        //用户存在
        //存在cookie中
        session.removeAttribute("code");
        Cookie cookie=new Cookie("_data", MyCookie.getCookie(name+password));
        cookie.setPath("/");
        cookie.setMaxAge(3600*24*7);//一周
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        manager.setCookieValue(cookie.getValue());
        managerService.addCookie(manager);//存入数据库
        message=new Message(1,"登录成功");
        return message;
        //return "index";

    }


}
