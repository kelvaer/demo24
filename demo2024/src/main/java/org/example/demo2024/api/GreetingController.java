//package org.example.demo2024.api;
//
//import org.example.demo2024.dto.InMessage;
//import org.example.demo2024.vo.OutMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//
///**
// * @SendTo 与 @SendToUser 是Spring的STOMP协议中注解的标签
// *
// * @SendTo
// * 会将接收到的消息发送到指定的路由目的地，所有订阅该消息的用户都能收到，属于广播
// *
// *@SendToUser
// * 消息目的地有UserDestinationMessageHandler来处理，会将消息路由到发送者对应的目的地。默认该注解前缀为/user。如：用户订阅/user/hi ，在@SendToUser('/hi')查找目的地时，会将目的地的转化为/user/{name}/hi, 这个name就是principal的name值，该操作是认为用户登录并且授权认证，使用principal的name作为目的地标识。发给消息来源的那个用户。（就是谁请求给谁，不会发给所有用户，区分就是依照principal-name来区分的)。
// * 此外该注解还有个broadcast属性，表明是否广播。就是当有同一个用户登录多个session时，是否都能收到。取值true/false.
// *
// *
// * @author lzh
// * @date 2020/10/21 - 16:11
// */
//@Controller
//public class GreetingController {
//    //SimpMessagingTemplate：SpringBoot提供操作WebSocket的对象
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/test")
//    public String ws(){
//        System.err.println("websocket test success!");
//        return "websocket test success!";
//    }
//
//
//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public String greeting(InMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
////        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//        return message.toString();
//    }
//
//    //----使用场景1--点对点单聊-----------------------------------------------
//    @CrossOrigin
//    @MessageMapping("/chat/message")
//    public void onChatMessage(@RequestBody InMessage inMessage){
//        System.err.println("点对点单聊"+inMessage.toString());
//        OutMessage outMessage = new OutMessage(  );
//        outMessage.setContent( inMessage.getContent() );
//        outMessage.setFromName( inMessage.getFromName() );
//        simpMessagingTemplate.convertAndSend( "/chat/"+inMessage.getToName(), outMessage);
//    }
//}
