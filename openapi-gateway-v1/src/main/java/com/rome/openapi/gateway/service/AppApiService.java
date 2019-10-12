/*
 * Filename AppApiService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.service;

import com.rome.openapi.gateway.domain.entity.ApiEntry;
import com.rome.openapi.gateway.domain.entity.AppEntry;

/**
 * 获取app和api服务
 *
 * @author kongweixiang
 * @since 1.0.0_2018/7/31
 */
public interface AppApiService {
    /**
     * 获取api信息并存入缓存
     *
     * @return
     */
    void getAllApi();

    /**
     * 获取app信息并存入缓存
     *
     * @return
     */
    void getAllApp();

    /**
     * 获取app,openapi,uriWhiteList信息并存入缓存
     */
    void getAllAppApi();

    void refreshCache();

    /**
     * 获取url白名单
     *
     * @return
     */
    void getUriWhiteList();


    String AppApiAuth(AppEntry app, ApiEntry api);
}
