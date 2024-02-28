//package org.example.demo2024.cfg.ws;
//
///**
// * @program: demo2024
// * @description: HttpHandShakeInterceptor
// * @author: 作者名
// * @create: 2024/02/05
// */
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.http.server.ServletServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.PrintWriter;
//import java.util.Map;
//
///**
// * @author lzh
// * @date 2020/10/21 - 17:42
// */
//@Component
//public class HttpHandShakeInterceptor implements HandshakeInterceptor {
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
//        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)serverHttpRequest;
//        HttpServletRequest request = servletRequest.getServletRequest();
//        HttpSession session = request.getSession();
//        System.out.println(session.getId());
////        Cookie[] cookies = request.getCookies();
////        for(int i=0;i<cookies.length;i++){
////            System.out.println("cookies-name:"+cookies[i].getName()+"cookies-value:"+cookies[i].getValue());
////        }
//        System.out.println("webSocket前置拦截器");
//
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
//        System.out.println("webSocket后置拦截器");
//        ServletServerHttpResponse resp = (ServletServerHttpResponse)serverHttpResponse;
//        HttpServletResponse servletResponse = resp.getServletResponse();
//
//
//    }
//}
