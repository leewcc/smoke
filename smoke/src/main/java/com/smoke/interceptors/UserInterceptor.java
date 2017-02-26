package com.smoke.interceptors;

import com.smoke.entity.Manager;
import com.smoke.service.ManagerService;
import com.smoke.utils.MyRSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;



/**
 * Created by CHEN on 2016/12/1.
 */
public class UserInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    private ManagerService managerService;


    /**
     * 在执行方法的时候 进行检查用户的存在状态
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("检查登录状态");
        //检查session中是否有这个用户
        HttpSession session =request.getSession();
        Manager manager=(Manager) session.getAttribute("user");//从session中取出user

        if(manager!=null) {//管理员存在
            return true;
        } else {//管理员不存在
           //检查cookie 使用rsa解密
            Cookie [] cookies=request.getCookies();
            for(Cookie c:cookies) {
                if((c.getName()).equals("_data")) {
                 /*   MyRSA rsa=new MyRSA();
                    String cookieStr=c.getValue();
                    byte[] en=cookieStr.getBytes("ISO-8859-1");
                    byte[] de=rsa.decrypt(rsa.getKeyPair().getPrivate(),en);
                    String idPassword=new String(de);
                    System.out.println(idPassword);
                    String [] is=idPassword.split(",");*/
                    Manager m=managerService.queryByCookie(c.getValue());
                    if(m!=null) {
                        session.setAttribute("user",m);
                        return true;
                    }
                }
            }
        }
        response.sendRedirect("/login");
        //request.getRequestDispatcher("/login").forward(request,response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
