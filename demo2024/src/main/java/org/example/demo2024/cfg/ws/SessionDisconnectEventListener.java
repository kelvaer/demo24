//package org.example.demo2024.cfg.ws;
//
///**
// * @program: demo2024
// * @description: SessionDisconnectEventListener
// * @author: 作者名
// * @create: 2024/02/05
// */
//
//import org.springframework.context.ApplicationListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
///**
// * Created by ldd on 2019/12/31.
// */
//@Component
//public class SessionDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent>{
//
//    /**
//     * 断开连接事件监听器
//     * @param sessionDisconnectEvent
//     */
//
//    @Override
//    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
//        StompHeaderAccessor wrap = StompHeaderAccessor.wrap( sessionDisconnectEvent.getMessage() );
//        System.err.println("断开连接事件监听器"+wrap.getCommand().getMessageType());
//    }
//}
//
