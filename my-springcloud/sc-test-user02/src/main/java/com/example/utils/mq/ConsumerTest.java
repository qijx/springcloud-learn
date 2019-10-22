//package com.example.utils.mq;
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.domain.User;
//import com.example.utils.RedisUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
///**
// * @author qijx
// * @date 2019-08-05 9:55
// */
//@Component
//@Slf4j
//@RocketMQMessageListener(topic="test-topic", selectorExpression = "test-tag", consumerGroup = "test-consumerGroup")
//public class ConsumerTest implements RocketMQListener<User> {
//    /**
//     * topic监听主题
//     * selectorExpression：消息的tag
//     * consumerGroup：消费者组，当监听的主题topic相同时，两个消费者的consumerGroup相同则两个消费者轮询消费topic消息
//     * 当两个消费者的consumerGroup不同时则每个消费者全量消费topicd消息
//     */
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Override
//    public void onMessage(User user) {
//        log.info("接受到的消息参数:{}", JSONObject.toJSONString(user));
//        Object record = redisUtil.get(RedisUtil.SEND_NEW_STORE_ACTIVITY );
//        /**
//         * 这里面做业务消息的处理工作
//         */
//        // 添加缓存做幂等，缓存时间5分钟
//        redisUtil.set(RedisUtil.SEND_NEW_STORE_ACTIVITY, user, RedisUtil.ONE_QUARTER);
//    }
//
//
//}
//
