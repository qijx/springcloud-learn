/*
 * Filename AppApiClient.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.client;

import com.rome.openapi.gateway.domain.entity.ApiEntry;
import com.rome.openapi.gateway.domain.entity.AppApiEntry;
import com.rome.openapi.gateway.domain.entity.AppEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 从后台系统获取有关app和api的相关信息
 *
 * @author kongweixiang
 * @since 1.0.0_2018/7/31
 */
@FeignClient("openapi-backend")
@RequestMapping(value = "/authSupport")
public interface AppApiClient {

    /**
     * 获取api信息
     *
     * @return
     */
    @RequestMapping(value = "/allApiInfo", method = RequestMethod.GET)
    List<ApiEntry> getApi();

    /**
     * 获取app信息
     *
     * @return
     */
    @RequestMapping(value = "/allAppInfo", method = RequestMethod.GET)
    List<AppEntry> getApp();

    /**
     * 获取url白名单
     *
     * @return
     */
    @RequestMapping(value = "/allUriWhiteList", method = RequestMethod.GET)
    List<String> getUriWhiteList();

    /**
     * 获取app的api权限
     *
     * @return
     */
    @RequestMapping(value = "/appapis", method = RequestMethod.GET)
    List<AppApiEntry> getAppApis();
}
