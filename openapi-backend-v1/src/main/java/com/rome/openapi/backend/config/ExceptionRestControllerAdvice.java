/*
 * Filename ExceptionControllerAdvice.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rome.openapi.backend.config.exception.BaseException;
import com.rome.openapi.backend.config.exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/23
 */
@RestControllerAdvice(annotations={RestController.class,ResponseBody.class})
public class ExceptionRestControllerAdvice {
	Logger logger = LoggerFactory.getLogger(ExceptionRestControllerAdvice.class);
	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	/**
	 * 全局异常捕捉处理
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value = Exception.class)
	public Map defaultErrorHandler(Exception ex) {
		logger.warn("请求异常",ex);
		Map map = new HashMap();
		map.put("code", 100);
		map.put("message", ex.getMessage());
		logger.warn("请求异常返回信息{}",gson.toJson(map));
		return map;
	}

	/**
	 * 拦截捕捉自定义异常 BaseException.class
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value = BaseException.class)
	public Map myErrorHandler(BaseException ex) {
		logger.warn("请求异常",ex);
		Map map = new HashMap();
		map.put("code", ex.getCode());
		map.put("message", ex.getMessage());
		logger.warn("请求异常返回信息{}",gson.toJson(map));
		return map;
	}


	/**
	 * 拦截捕捉自定义异常 BaseException.class
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value = ValidateException.class)
	public Map myErrorHandler(ValidateException ex) {
		logger.warn("请求数据校验异常",ex);
		Map map = new HashMap();
		map.put("code", ex.getCode());
		map.put("message", ex.getMessage());
		logger.warn("请求数据校验异常返回信息{}",gson.toJson(map));
		return map;
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(ValidationException.class)
	public Map myErrorHandler(ValidationException ex) {
		logger.warn("请求数据Validation校验异常",ex);
		String message = "";
		if(ex instanceof ConstraintViolationException){
			ConstraintViolationException exs = (ConstraintViolationException) ex;

			Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
			for (ConstraintViolation<?> item : violations) {
				message = item.getMessage();
				break;
			}
		}
		Map map = new HashMap();
		map.put("code", "101101");
		map.put("message", message);
		logger.warn("请求Validation异常返回信息{}",gson.toJson(map));
		return map;
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map myErrorHandler(MethodArgumentNotValidException ex) {
		logger.warn("请求数据绑定异常",ex);
		String message = "";
		BindingResult bindingResult = ex.getBindingResult();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			message = fieldError.getDefaultMessage();
			break;
		}
		Map map = new HashMap();
		map.put("code", "101101");
		map.put("message", message);
		logger.warn("请求数据绑定异常返回信息{}",gson.toJson(map));
		return map;
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(BindException.class)
	public Map myErrorHandler(BindException ex) {
		logger.warn("请求数据校验绑定异常",ex);
		String message = "";
		BindingResult bindingResult = ex.getBindingResult();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			message = fieldError.getDefaultMessage();
			break;
		}
		Map map = new HashMap();
		map.put("code", "101101");
		map.put("message", message);
		logger.warn("请求数据校验绑定异常返回信息{}",gson.toJson(map));
		return map;
	}

}
