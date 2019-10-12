package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.example.demo")
@EnableSwagger2
public class ElasticJobDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticJobDemoApplication.class, args);
	}

}