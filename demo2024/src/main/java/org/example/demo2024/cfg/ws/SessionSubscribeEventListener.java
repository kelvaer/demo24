//package org.example.demo2024.cfg.ws;
//
///**
// * @program: demo2024
// * @description: SessionSubscribeEventListener
// * @author: 作者名
// * @create: 2024/02/05
// */
//
//import org.springframework.context.ApplicationListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionSubscribeEvent;
//
///**
// * Created by ldd on 2019/12/31.
// */
//@Component
//public class SessionSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent>{
//
//    /**
//     * 订阅事件监听器
//     * @param sessionSubscribeEvent
//     */
//
//    @Override
//    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
//        StompHeaderAccessor wrap = StompHeaderAccessor.wrap( sessionSubscribeEvent.getMessage() );
//        System.err.println("订阅事件监听器"+wrap.getCommand().getMessageType());
//    }
//}
//
