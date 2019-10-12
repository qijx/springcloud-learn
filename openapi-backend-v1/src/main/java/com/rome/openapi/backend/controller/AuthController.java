/*
 * Filename AuthController.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.controller;

import com.rome.openapi.backend.service.AppApiService;
import com.rome.openapi.backend.service.OpenApiService;
import com.rome.openapi.backend.service.OpenAppService;
import com.rome.openapi.backend.service.UriWhiteListService;
import com.rome.openapi.backend.vo.ApiEntry;
import com.rome.openapi.backend.vo.AppApiEntry;
import com.rome.openapi.backend.vo.AppEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 网关鉴权获取app商家接口权限数据管理
 *
 * 为auth系统提供服务的入口
 * 给网关pool调用使用
 * @since 1.0.0_2018/7/31
 */
@RestController
@RequestMapping(value = "/authSupport")
public class AuthController {
	private final Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	OpenApiService openApiService;
	@Autowired
	OpenAppService openAppService;
	@Autowired
	UriWhiteListService uriWhiteListService;
	@Autowired
	AppApiService appApiService;
	/**
	 * 获取api信息
	 * @return
	 */
	@RequestMapping(value = "/allApiInfo", method = RequestMethod.GET)
	public List<ApiEntry> getApi() {
		List<ApiEntry> list = openApiService.getApiWithApp();
		return list;
	}

	/**
	 * 获取app信息
	 * @return
	 */
	@RequestMapping(value = "/allAppInfo", method = RequestMethod.GET)
	public List<AppEntry> getApp() {
		List<AppEntry> list = openAppService.getAppWithIP();
		return list;
	}

	@RequestMapping(value = "/appapis", method = RequestMethod.GET)
	public List<AppApiEntry> getAppApi(){
		return appApiService.queryAppAPis();
	}

	/**
	 * 获取url白名单
	 * @return
	 */
	@RequestMapping(value = "/allUriWhiteList", method = RequestMethod.GET)
	public List<String> getallUriWhiteList() {
		List<String> list = uriWhiteListService.getAllListOnlyUri();
		return list;
	}

}
