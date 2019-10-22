//package com.example.scqijx.service
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RedisService {
//    @Autowired
//    private StringRedisTemplate template;
//
//    public void setData(String key, String data){
//        template.opsForValue().set(key, data);
//        template.expire(key, 5, TimeUnit.SECONDS);
//    }
//
//    public String getData(String key) {
//        return template.opsForValue().get(key);
//    }
//}