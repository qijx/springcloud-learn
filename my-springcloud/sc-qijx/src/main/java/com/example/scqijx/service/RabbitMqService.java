//package com.example.scqijx.service
//
//import javax.annotation.PostConstruct;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//
//@Service
//public class RabbitMqService {
//
//    @Autowired
//    private AmqpAdmin rabbitAdmin;
//
//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//
//    private static String queueName = "test";
//    private static String exchangeName = "test";
//
//    @PostConstruct
//	public void  bindQueu() {
//		if (rabbitAdmin.getQueueProperties(queueName) == null) {
//            /*  queue 队列声明
//            durable=true,交换机持久化,rabbitmq服务重启交换机依然存在,保证不丢失; durable=false,相反
//            auto-delete=true:无消费者时，队列自动删除; auto-delete=false：无消费者时，队列不会自动删除
//            排他性，exclusive=true:首次申明的connection连接下可见; exclusive=false：所有connection连接下*/
//
//            Queue queue = new Queue(queueName, true, false, false, null);
//            rabbitAdmin.declareQueue(queue);
//            //上面是最基础的用法，绑定的默认exchange，没有用到routekey等，如果需要，将下面代码注释打开
//            // TopicExchange directExchange = new TopicExchange(exchangeName);
//            // rabbitAdmin.declareExchange(directExchange);//声明exchange
//            // Binding binding = BindingBuilder.bind(queue).to(directExchange).with(queueName);    //将queue绑定到exchange
//            // rabbitAdmin.declareBinding(binding);      //声明绑定关系
//
//        }
//    }
//
//	public void sendMsg(String msg) {
//        rabbitTemplate.convertAndSend(null, queueName, msg);
//		//rabbitTemplate.convertAndSend("exchange", "routingKey", msg);
//    }
//
//    @RabbitListener(queues = "test")
//    public void receiveMessage(final String data) {
//        System.out.println("Received message:: "+data);
//    }
//}