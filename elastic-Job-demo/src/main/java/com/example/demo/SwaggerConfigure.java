package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigure {

	@Bean 
	public Docket customImplementation(){
	    return new Docket(DocumentationType.SWAGGER_2)
	        .apiInfo(apiInfo())
	       // .enable(Boolean.TRUE) //<--- Flag to enable or disable possibly loaded using a property file
	        .select().apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
	        .paths(PathSelectors.any()).build();
	    	
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot中使用Swagger")
				.description("测试描述")
				.contact("demo")
				.version("1.0")
				.build();
	}
	
}
