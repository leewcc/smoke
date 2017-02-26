package com.smoke.websocket;

import com.smoke.utils.RegularUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.SchemaOutputResolver;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类定义在建立 websocket 连接握手时，需要进行的操作
 * Created by asus2015 on 2016/11/29.
 */
public class SmokeHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        System.out.println("有用户开始尝试");

        if(serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpServletRequest request = servletRequest.getServletRequest();

            // 获取用户需要查看的老师工号，以便知道此次连接是查看哪个用户的设备信息
            String tid = request.getParameter("userId");

            // 判断用户 id 是否正确，若不正确，握手失败，若正确，握手成功
            if(!RegularUtil.isNumber(tid)) {
                return false;
            }

            // 将用户id添加进map集合中，以便供后续的处理器使用
            map.put("userId", Integer.parseInt(tid));

            return true;
        }

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        HashMap
    }
}
