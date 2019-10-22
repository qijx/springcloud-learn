package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringCloudApplication
//@EnableTransactionManagement
//@MapperScan(basePackages={"com.example.mapper"})
@EnableSwagger2
@EnableFeignClients
//@EnableAsync  开启异步注解
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }








}
