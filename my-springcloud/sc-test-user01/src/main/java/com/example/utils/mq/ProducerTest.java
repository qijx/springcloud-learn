//package com.example.utils.mq;
//
//import com.alibaba.fastjson.JSON;
//import com.example.domain.User;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.client.producer.SendStatus;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @author qijx
// * @date 2019-10-21 16:30
// */
//@Slf4j
//@Component
//public class ProducerTest {
//
//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//
//    private static final String MESSAGE_TOPIC="test-topic";
//
//    private static final String MESSAGE_TAG="test-tag";
//
//
//    public void sendMessageTest(){
//        User user = new User();
//        user.setName("mq测试消息");
//        SendResult sendResult = rocketMQTemplate.syncSend(MESSAGE_TOPIC+":"+MESSAGE_TAG, user, 5000);
//        log.info("发送消息内容 : {},发送结果 : {}",JSON.toJSONString(sendResult),sendResult.getSendStatus());
//        int result=sendResult.getSendStatus()==SendStatus.SEND_OK?1:0;
//        System.out.println(result);
//    }
//
//
//
//}
