/*
 * Filename BackendWebApplication.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/24
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.rome.openapi.backend.mapper"})
@EnableTransactionManagement
@EnableSwagger2
//@EnableAsync
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
