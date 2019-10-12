package com.example.springboot.demo.mq.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author qijx
 * @date 2019-08-06 11:09
 */

@Component
@Slf4j
public class RocketMqProducer {


    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private static final String topic="promotion-test-msg";

    public static final String selectorExpression="promotion-test-activity";


    @Async
    public void sendMessage(String s){
        log.info("接受到的参数为：{}",s);
        Message message = new Message();
        message.setString(s);
        rocketMQTemplate.syncSend(topic+":"+selectorExpression,message,2000);
    }
}
