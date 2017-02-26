package com.smoke.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 该类定义 websocket 的配置
 * 定义链接路径、握手拦截器以及处理器
 * Created by asus2015 on 2016/11/29.
 */
@Component
@EnableWebSocket
public class SmokeWebsocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Autowired
    SmokeWebSocketHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //添加websocket处理器，添加握手拦截器
        webSocketHandlerRegistry.addHandler(handler, "/ws").addInterceptors(new SmokeHandshakeInterceptor());

        //添加websocket处理器，添加握手拦截器
        webSocketHandlerRegistry.addHandler(handler, "/ws/sockjs").addInterceptors(new SmokeHandshakeInterceptor()).withSockJS();
    }
}