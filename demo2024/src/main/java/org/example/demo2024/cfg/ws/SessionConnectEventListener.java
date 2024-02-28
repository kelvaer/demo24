//package org.example.demo2024.cfg.ws;
//
///**
// * @program: demo2024
// * @description: SessionConnectEventListener
// * @author: 作者名
// * @create: 2024/02/05
// */
//import org.springframework.context.ApplicationListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//
///**
// * Created by ldd on 2019/12/31.
// */
//@Component
//public class SessionConnectEventListener implements ApplicationListener<SessionConnectEvent>{
//
//    /**
//     * 连接事件监听器
//     * @param sessionConnectEvent
//     */
//
//    @Override
//    public void onApplicationEvent(SessionConnectEvent sessionConnectEvent) {
//        StompHeaderAccessor wrap = StompHeaderAccessor.wrap( sessionConnectEvent.getMessage() );
//        System.err.println("连接事件监听器"+wrap.getCommand().getMessageType());
//    }
//}