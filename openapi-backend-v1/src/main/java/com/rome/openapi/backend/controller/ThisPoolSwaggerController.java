/*
 * Filename LYFSwaggerController.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.controller;

import com.rome.openapi.backend.service.SwaggerResolveApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;

/**
 * 当访问该服务（openapi-backend-v1）的swagger页面时会加载该类的
 * @date 2018/8/17
 * @since 1.0.0
 */
@Controller
public class ThisPoolSwaggerController {

	@Autowired
	private SwaggerResolveApiService swaggerResolveApiService;


	/**
	 * 当访问该服务的swagger时会调用此方法，次方法覆盖了swagger的默认调用方法
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/swagger-resources",method = RequestMethod.GET)
	public List<SwaggerResource> getSwaggerResource(){
		return swaggerResolveApiService.getSwaggerResoure();
	}


	/**
	 * 调用完上面方法则调用下面这个方法
	 * @param service
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "showApi/{service}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getApiJson(@PathVariable String service) {
		return this.swaggerResolveApiService.showApi(service, null);
	}
}
