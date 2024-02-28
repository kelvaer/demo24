//package org.example.demo2024.cfg.ws;
//
//import com.google.common.base.Joiner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author lzh
// * @date 2020/10/21 - 16:08
// */
//@Configuration
//
////@EnableWebSocketMessageBroker注解
//// 开启使用STOMP协议来传输基于代理(message broker)的消息,
//// 这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
//@EnableWebSocketMessageBroker
//public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
//
//    ////注册STOMP协议的节点(endpoint),并映射指定的url
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//
////        List<String> allowedOrigins = Arrays.asList("http://localhost:8080");
//
//        //注册一个STOMP的endpoint,并指定使用SockJS协议
//        registry.
//                //添加一个访问端点“/endpointGym”,客户端打开双通道时需要的url
//                        addEndpoint("/ws")
//                //允许所有的域名跨域访问
////                .setAllowedOrigins(Joiner.on(",").join(allowedOrigins))
//                .setAllowedOriginPatterns("*")
//                //webSocket拦截器
//                .addInterceptors(new HttpHandShakeInterceptor());
//                //指定使用SockJS协议
////                .withSockJS();
//
//        //js 连接webSocket    let socket = new SockJS("http://localhost:9191/ws-vue");
//    }
//
//
//    /**
//     * 配置消息代理（中介）
//     * enableSimpleBroker:服务端推送给客户端的路径前缀
//     * setApplicationDestinationPrefixes:客户端发送给服务端的路径前缀
//     *
//     * @param registry
//     */
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        //用户可以订阅来自"/topic" "/chat",/test的消息，
//        //在Controller中，可通过@SendTo注解指明发送目标，这样服务器就可以将消息发送到订阅相关消息的客户端
//        registry.enableSimpleBroker("/topic", "/chat","/test");
//
//
//        //设置客户端订阅消息的基础路径
//        //客户端发送过来的消息，需要以"/app"为前缀，再经过Broker转发给响应的Controller
//        //      this.stompClient.send("/app/chat/message", {}, JSON.stringify(obj));
//        registry.setApplicationDestinationPrefixes("/app");
//    }
//
//
//
//
//
//    //配置客户端进入通道
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new SocketChannelInterceptor());
//    }
//
//    //配置客户端退出通道
//    @Override
//    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new SocketChannelInterceptor());
//    }
//}
