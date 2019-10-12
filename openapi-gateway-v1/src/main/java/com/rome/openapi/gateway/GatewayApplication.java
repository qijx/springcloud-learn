package com.rome.openapi.gateway;

import com.rome.openapi.gateway.filter.InnerFilter;
import com.rome.openapi.gateway.filter.KibanaLogFilter;
import com.rome.openapi.gateway.filter.ResponseBodyFilter;
import com.rome.openapi.gateway.filter.SignFilter;
import com.rome.openapi.gateway.filter.StaticSourceFilter;
import com.rome.openapi.gateway.filter.WriteUrlFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableFeignClients
@EnableScheduling
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 检验签名拦截器
     * @return
     */
    @Bean
    public SignFilter signFilter() {
        return new SignFilter();
    }


    @Bean
    public InnerFilter innerFilter() {
        return new InnerFilter();
    }

    @Bean
    public WriteUrlFilter writeUrlFilter() {
        return new WriteUrlFilter();
    }

    @Bean
    public KibanaLogFilter kibanaLogFilter() {
        return new KibanaLogFilter();
    }

//        @Bean
//    public SignatureVerifyFilter signatureVerifyFilter() {
//        return new SignatureVerifyFilter();
//    }
    @Bean
    public StaticSourceFilter staticSourceFilter() {
        return new StaticSourceFilter();
    }

    @Bean
    public ResponseBodyFilter responseBodyFilter() {
        return new ResponseBodyFilter();
    }

}
