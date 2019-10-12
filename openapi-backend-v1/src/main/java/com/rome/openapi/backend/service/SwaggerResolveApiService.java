/*
 * Filename SwaggerResolveApiService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service;

import com.rome.openapi.backend.common.annotation.AuthCacheFresh;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;

/**
 * 解析并注册swagger注册的api服务
 * @author kongweixiang
 * @since 1.0.0_2018/8/3
 */
public interface SwaggerResolveApiService {

	/**
	 * 通过serviceId获取swagger信息并注册api服务
	 *
	 * @param serviceId
	 */
	@AuthCacheFresh
	void registerAPI(Integer serviceId);

	/**
	 * 获取服务的api-docs（接口文档）
	 * @param serviceId 服务
	 * @param group api组
	 * @return
	 */
	String showApi(String serviceId, String group);

	/**
	 * 获取swagger的资源
	 * @return
	 */
	List<SwaggerResource> getSwaggerResoure();
}
