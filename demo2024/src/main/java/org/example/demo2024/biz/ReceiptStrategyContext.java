package org.example.demo2024.biz;

import lombok.extern.slf4j.Slf4j;
import org.example.demo2024.dto.Receipt;
import org.springframework.stereotype.Component;

/**
 * @program: demo2024
 * @description: ctx
 * @author: 作者名
 * @create: 2024/02/06
 */
@Component
@Slf4j
public class ReceiptStrategyContext {

    private ITopicHandleStrategy topicHandleStrategy;

    /**
     * 1.Context 选择设置Strategy策略
     * @param topicHandleStrategy
     */
    public void setTopicHandleStrategy(ITopicHandleStrategy topicHandleStrategy) {
        log.debug("Context 设置Strategy策略");
        this.topicHandleStrategy = topicHandleStrategy;
    }

    /**
     * 2.执行策略对应的业务
     * @param receipt
     */
    public void handleReceipt(Receipt receipt){
        if (topicHandleStrategy != null){
            log.debug("Context 执行策略对应的业务");
            topicHandleStrategy.handelReceiveTopic(receipt);
        }
    }
}
