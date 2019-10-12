/*
 * Filename ApiService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.service;

import com.rome.openapi.gateway.domain.entity.ApiEntry;
import com.rome.openapi.gateway.domain.entity.AppEntry;

/**
 * api及app验证服务
 *
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
public interface ApiService {

    /**
     * app白名单认证
     * 判断app是否存在以及appSecret是否正确
     *
     * @param appKey
     * @param IP
     * @return
     */
    boolean appIPWhiteListAuth(String appKey, String IP);


    AppEntry appAuth(String appid, String IP);


    /**
     * api验证，是否存在该api
     *
     * @param uri
     * @param method
     * @return
     */
    ApiEntry apiAuth(String uri, String method);
}
