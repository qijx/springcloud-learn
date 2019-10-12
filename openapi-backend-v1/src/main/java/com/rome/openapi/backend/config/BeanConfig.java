/*
 * Filename BeanConfig.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 远程调用
 * @since 1.0.0_2018/8/8
 */
@Configuration
public class BeanConfig {

	/**
	 * 使用restTemplate代替fegin远程调用
	 * 使用@LoadBalanced注解代替ribbon负载均衡调用
	 * @return
	 */
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
