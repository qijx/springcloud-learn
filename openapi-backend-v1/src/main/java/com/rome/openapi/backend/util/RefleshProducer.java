//package com.rome.openapi.backend.message;
//
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @Class: RefleshProducer
// * @Auther: MarsKen
// * @Date: 2019/5/12 15:13
// * @Description:
// */
//@Component
//public class RefleshProducer {
//    private static final String topic = "arch-topic";
//    private static final String tag = "arch-reflesh";
//
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//    public void refleshApi(String serviceId) {
//        rocketMQTemplate.convertAndSend(topic + ":" + tag, serviceId);
//    }
//
//}
