//package com.rome.openapi.gateway.message;
//
//import com.rome.openapi.gateway.service.AppApiService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.util.Enumeration;
//import java.util.List;
//
///**
// * @Class: RefleshConsumer
// * @Auther: MarsKen
// * @Date: 2019/5/13 20:03
// * @Description:
// */
//@Slf4j
//@Component
//public class RefleshConsumer implements ApplicationListener<ContextRefreshedEvent> {
//    private static final String topic = "arch-topic";
//    private static final String tag = "arch-reflesh";
//
//    @Autowired
//    private Environment environment;
//
//    @Autowired
//    private AppApiService appApiService;
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        try {
//            // 生成了两个AnnotationConfigApplicationContext子容器；
//            //父容器为AnnotationConfigEmbeddedWebApplicationContext；
//            //祖父容器为AnnotationConfigApplicationContext
//            ApplicationContext child = contextRefreshedEvent.getApplicationContext();
//            ApplicationContext parent = contextRefreshedEvent.getApplicationContext().getParent();
//            ApplicationContext grandParent = parent != null ? parent.getParent() : null;
//            log.info("当前：{}; 父亲：{}; 祖父：{}", child.getDisplayName(), parent == null ? null : parent.getDisplayName(),
//                    grandParent == null ? null : grandParent.getDisplayName());
//            if (contextRefreshedEvent.getApplicationContext().getParent().getParent() == null) {
//                log.info("初始化适配器开始");
//                listener(topic, tag);
//                log.info("初始化适配器完成");
//            }
//        } catch (MQClientException e) {
//            log.error("消费者监听器启动失败", e);
//        }
//    }
//
//
//    // 开启消费者监听服务
//    public void listener(String topic, String tag) throws MQClientException {
//        log.info("开启" + topic + ":" + tag + "消费者-------------------");
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(getLocalIpAddr().replace(".", "_"));
//        consumer.setNamesrvAddr(environment.getProperty("rocketmq.name-server"));
//        consumer.subscribe(topic, tag);
//        // 开启内部类实现监听
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                return dealBody(msgs);
//            }
//        });
//        consumer.start();
//        log.info("rocketmq启动成功---------------------------------------");
//    }
//
//    public ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs) {
//        try {
//            msgs.forEach(messageExt -> {
//                log.info("received messageExt: {}", messageExt);
//                appApiService.refreshCache();
//                log.info("received messageExt: {}", messageExt.getTags());
//            });
//        } catch (Exception e) {
//            log.error("消费消息异常", e);
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //稍后再试
//        }
//        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
//    }
//
//
//    public static String getLocalIpAddr() {
//        String clientIP = null;
//        Enumeration<NetworkInterface> networks = null;
//        try {
//            //获取所有网卡设备
//            networks = NetworkInterface.getNetworkInterfaces();
//            if (networks == null) {
//                //没有网卡设备 打印日志  返回null结束
//                log.info("networks  is null");
//                return null;
//            }
//        } catch (SocketException e) {
//            log.error(e.getMessage());
//        }
//        InetAddress ip;
//        Enumeration<InetAddress> addrs;
//        // 遍历网卡设备
//        while (networks.hasMoreElements()) {
//            NetworkInterface ni = networks.nextElement();
//            try {
//                //过滤掉 loopback设备、虚拟网卡
//                if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
//                    continue;
//                }
//            } catch (SocketException e) {
//                log.info(e.getMessage());
//            }
//            addrs = ni.getInetAddresses();
//            if (addrs == null) {
//                log.info("InetAddress is null");
//                continue;
//            }
//            // 遍历InetAddress信息
//            while (addrs.hasMoreElements()) {
//                ip = addrs.nextElement();
//                if (!ip.isLoopbackAddress() && ip.isSiteLocalAddress() && ip.getHostAddress().indexOf(":") == -1) {
//                    try {
//                        clientIP = ip.toString().split("/")[1];
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        clientIP = null;
//                    }
//                }
//            }
//        }
//        return clientIP;
//    }
//}
