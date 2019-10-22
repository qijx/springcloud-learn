//package com.example.scqijx.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.ClusterServersConfig;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//
//@Configuration
//@Slf4j
//public class RedisConfig {
//
//    @Value("${spring.redis.cluster.nodes}")
//    private String cluster;
//    @Value("${spring.redis.password}")
//    private String password;
//
//
//    @Bean
//    public RedisTemplate setRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        // 设置value的序列化规则和 key的序列化规则
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//
//    @Bean
//    public RedissonClient getRedissonClient() {
//        String[] nodes = cluster.split(",");
//        // redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
//        for (int i = 0; i < nodes.length; i++) {
//            nodes[i] = "redis://" + nodes[i].trim();
//        }
//        RedissonClient redisson = null;
//        Config config = new Config();
//        // 这是用的集群server
//        // 设置集群状态扫描时间
//        // 设置主从连接池大小和最小连接数量
//        ClusterServersConfig clusterServersConfig = config.useClusterServers()
//                .setScanInterval(2000)
//                .addNodeAddress(nodes)
//                .setMasterConnectionPoolSize(30)
//                .setMasterConnectionMinimumIdleSize(8)
//                .setSlaveConnectionPoolSize(10)
//                .setSlaveConnectionMinimumIdleSize(8)
//                .setConnectTimeout(10000)
//                .setTimeout(3000);
//        if (!"".equals(password)) {
//            clusterServersConfig.setPassword(password);
//        }
//        redisson = Redisson.create(config);
//        try {
//            log.info("redisson配置：{}", redisson.getConfig().toJSON());
//        } catch (Exception e) {
//            // ignore
//        }
//        //可通过打印redisson.getConfig().toJSON().toString()来检测是否配置成功
//        return redisson;
//    }
//
//}
