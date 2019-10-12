/*
 * Filename ValidationExceptionConfig.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * HibernateValidator 数据校验相关配置
 * @author kongweixiang
 * @since 1.0.0_2018/7/24
 */
@Configuration
public class ValidationExceptionConfig {

	/**
	 * 配置支持@RequestParam的校验支持
	 * 注意，在非bean的校验时，方法所在的Controller上加注解@Validated
	 * @return
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
		 /**设置validator模式为快速失败返回*/

		ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
				.configure()
				.addProperty( "hibernate.validator.fail_fast", "true" )
				.buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		postProcessor.setValidator(validator);
		return postProcessor;
	}

//	/**
//	 * 配置快速失败返回模式
//	 * @return
//	 */
//	@Bean
//	public Validator validator(){
//		ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
//				.configure()
//				.addProperty( "hibernate.validator.fail_fast", "true" )
//				.buildValidatorFactory();
//		Validator validator = validatorFactory.getValidator();
//
//		return validator;
//	}
}
