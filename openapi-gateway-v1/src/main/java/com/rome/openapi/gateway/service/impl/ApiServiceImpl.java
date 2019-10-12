/*
 * Filename ApiServiceImpl.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.service.impl;

import com.rome.arch.core.exception.RomeException;
import com.rome.openapi.gateway.constant.CacheManage;
import com.rome.openapi.gateway.constant.CodeMessage;
import com.rome.openapi.gateway.domain.entity.ApiEntry;
import com.rome.openapi.gateway.domain.entity.AppEntry;
import com.rome.openapi.gateway.service.ApiService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
@Service
public class ApiServiceImpl implements ApiService {
    private final Logger log = LoggerFactory.getLogger(ApiServiceImpl.class);
    @Autowired
    private CacheManage cacheManage;


    @Override
    public boolean appIPWhiteListAuth(String appKey, String IP) {
        this.log.info("app IPWhiteListAuth validation start appKey{}", appKey);
        //获取APP信息
        Map<String, AppEntry> appMap = cacheManage.getAppList();
        if (appMap == null || appMap.size() == 0) {
            this.log.error("app cache is null");
            throw new RomeException(CodeMessage.SYSERR.getCode(), CodeMessage.SYSERR.getMessage());
        }
        if (!appMap.containsKey(appKey)) {
            this.log.warn("appId{} is not exist", appKey);
            throw new RomeException(CodeMessage.APIFORBIDDEN.getCode(), CodeMessage.APIFORBIDDEN.getMessage());
        }
        AppEntry appEntry = appMap.get(appKey);
        //如果需要验证ip白名单
        if (appEntry.getHasWhiteList()) {
            if (StringUtils.isNotBlank(appEntry.getIP())) {
                String[] ipList = appEntry.getIP().split(",");
                //验证ip白名单
                for (String i : ipList) {
                    if (StringUtils.equals(i, IP)) {
                        this.log.info("app IPWhiteListAuth validation end...");
                        return true;
                    }
                }
            }
            //如果白名单验证不通过
            this.log.warn("ip{} is not in appKey{}'s ipWhiteList", IP, appKey);
            throw new RomeException(CodeMessage.IPWHITENOTEXIST.getCode(), CodeMessage.IPWHITENOTEXIST.getMessage());
        }
        return true;
    }

    @Override
    public AppEntry appAuth(String appid, String IP) {
        this.log.info("app IPWhiteListAuth validation start appKey {}", appid);
        //获取APP信息
        Map<String, AppEntry> appMap = cacheManage.getAppList();
        if (appMap == null || appMap.size() == 0) {
            this.log.error("app cache is null");
            throw new RomeException(CodeMessage.SYSERR.getCode(), CodeMessage.SYSERR.getMessage());
        }
        if (!appMap.containsKey(appid)) {
            this.log.error("appId{} is not exist", appid);
            throw new RomeException(CodeMessage.APIFORBIDDEN.getCode(), CodeMessage.APIFORBIDDEN.getMessage());
        }
        AppEntry app = appMap.get(appid);

        if (app.getHasWhiteList()) {
            String ips = app.getIP();
            if (false == (StringUtils.isNotEmpty(ips) && ips.indexOf(IP) != -1)) {
                log.warn("ip白名单不包含IP：{}", IP);
                throw new RomeException(CodeMessage.IPWHITENOTEXIST.getCode(), CodeMessage.IPWHITENOTEXIST.getMessage());
            }
        }

        return app;
    }

    @Override
    public ApiEntry apiAuth(String uri, String method) {
        log.info("api校验开始，入参uri:{},method:{}", uri, method);

        Map<Integer, ApiEntry> apiList = cacheManage.getApiList();
        if (apiList == null || apiList.size() == 0) {
            this.log.error("api cache is null");
            throw new RomeException(CodeMessage.SYSERR.getCode(), CodeMessage.SYSERR.getMessage());
        }
        //获取url匹配的apiId
        int apiId = pathURLResolver(uri, method, apiList);

        //如果未匹配到api
        if (apiId == 0) {
            this.log.error("没有匹配到uri:{}", uri);
            throw new RomeException(CodeMessage.NOFINDAPI.getCode(), CodeMessage.NOFINDAPI.getMessage());
        }

        //api匹配成功
        ApiEntry apiEntry = apiList.get(apiId);

        return apiEntry;
    }


    /**
     * 根据访问路径匹配api
     *
     * @param url 访问url
     * @return api id
     */
    protected int pathURLResolver(String url, String httpMethod, Map<Integer, ApiEntry> apiEntry) {
        String apiURL;
        if (url.indexOf("?") >= 0) {
            url = url.substring(0, url.indexOf("?"));
        }
        for (Integer appId : apiEntry.keySet()) {
            ApiEntry api = apiEntry.get(appId);
            apiURL = api.getPath().replaceAll("\\{\\w+\\}", "\\\\w+");
            if (url.matches(apiURL) && StringUtils.equalsIgnoreCase(httpMethod, api.getMethod())) {
                return appId;
            }
        }
        return 0;
    }

}
