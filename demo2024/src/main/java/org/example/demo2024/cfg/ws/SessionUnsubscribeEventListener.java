//package org.example.demo2024.cfg.ws;
//
///**
// * @program: demo2024
// * @description: SessionUnsubscribeEventListener
// * @author: 作者名
// * @create: 2024/02/05
// */
//
//import org.springframework.context.ApplicationListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
//
///**
// * Created by ldd on 2019/12/31.
// */
//@Component
//public class SessionUnsubscribeEventListener implements ApplicationListener<SessionUnsubscribeEvent>{
//
//    /**
//     * 取消订阅事件监听器
//     * @param sessionUnsubscribeEvent
//     */
//
//    @Override
//    public void onApplicationEvent(SessionUnsubscribeEvent sessionUnsubscribeEvent) {
//        StompHeaderAccessor wrap = StompHeaderAccessor.wrap( sessionUnsubscribeEvent.getMessage() );
//        System.err.println("取消订阅事件监听器"+wrap.getCommand().getMessageType());
//    }
//}
//
