package org.example.demo2024.biz.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.biz.ITopicHandleStrategy;
import org.example.demo2024.biz.ReceiptHandleStrategyFactory;
import org.example.demo2024.dto.Receipt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @program: demo2024
 * @description: ws 上报
 * @author: 作者名
 * @create: 2024/02/06
 */
@Slf4j
@Service
public class Post1HandleStrategy implements ITopicHandleStrategy, InitializingBean {
    @Override
    public void handelReceiveTopic(Receipt receipt) {
        String ip = receipt.getConn().getRemoteSocketAddress().getAddress().getHostAddress();
//        log.info("客户端请求的ip:{}", ip);
        int port = receipt.getConn().getRemoteSocketAddress().getPort();
//        log.info("客户端请求的port:{}", port);
//        log.warn("topic:{} 收到消息: {}",receipt.getTopic(),receipt.getMsg());
        //do something
        log.info("客户端ws://{}:{}{} 上报了消息{}",ip,port,receipt.getTopic(),receipt.getMsg());


        //接收完信息可以回复 确认消息 ，也可以不回复
//        WsktUtil.publishMsgToClient("ok",receipt.getConn());

    }





    @Override
    public void afterPropertiesSet() throws Exception {
        ReceiptHandleStrategyFactory.register("/p1",this);
    }
}
