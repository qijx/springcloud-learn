package com.example.springboot.demo.mq.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author qijx
 * @date 2019-08-06 11:09
 */
@Slf4j
@Component
@RocketMQMessageListener(topic="promotion-test-msg", selectorExpression = "promotion-test-activity", consumerGroup = "promotion-test-group")
public class RocketMyCousmer implements RocketMQListener<Message> {



    @Override
    public void onMessage(Message message) {

        log.info("消费者接受到的消息为：{}",message);


    }
}
