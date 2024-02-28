package org.example.demo2024.biz;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: demo2024
 * @description: gy
 * @author: 作者名
 * @create: 2024/02/06
 */
@Slf4j
public class ReceiptHandleStrategyFactory {
    private static Map<String, ITopicHandleStrategy> topicHandleStrategyMap = new ConcurrentHashMap<>();


    public static ITopicHandleStrategy getReceiptHandleStrategy(String receiptTopic) {
        log.debug("---------从策略工厂拿出topic:{}对应的策略",receiptTopic);
        for (String key : topicHandleStrategyMap.keySet()) {
            if (StrUtil.startWith(receiptTopic,key)) {
                ITopicHandleStrategy strategy = topicHandleStrategyMap.get(key);
                log.debug("拿到了策略:{}",strategy.getClass().getName());
                return strategy;
            }
        }
        log.error("没拿到对应的策略类");
        return null;
    }

    public static void register(String topic,ITopicHandleStrategy itemStrategy){
        log.warn("InitializingBean 把topic:{}对应的策略类set进策略工厂",topic);
        topicHandleStrategyMap.put(topic,itemStrategy);
    }
}
