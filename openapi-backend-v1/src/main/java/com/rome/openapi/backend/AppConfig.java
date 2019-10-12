package com.rome.openapi.backend;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan({"com.rome.arch"})
public class AppConfig {

//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.rome.trade.api.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Roma Project trade-core-order-fulfillment中使用Swagger2构建RESTful APIs")
//                .description("trade-core-order-fulfillment接口的描述信息")
//                .contact("trade-core-order-fulfillment")
//                .version("1.0.0-SNAPSHOT")
//                .build();
//    }
}
