/*
 * Filename AppApiServiceImpl.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.service.impl;

import com.rome.arch.core.exception.RomeException;
import com.rome.openapi.gateway.client.AppApiClient;
import com.rome.openapi.gateway.constant.CacheManage;
import com.rome.openapi.gateway.constant.CodeMessage;
import com.rome.openapi.gateway.domain.entity.ApiEntry;
import com.rome.openapi.gateway.domain.entity.AppApiEntry;
import com.rome.openapi.gateway.domain.entity.AppEntry;
import com.rome.openapi.gateway.service.AppApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/31
 */
@Service
public class AppApiServiceImpl implements AppApiService {
    private final Logger log = LoggerFactory.getLogger(AppApiServiceImpl.class);

    @Resource
    private AppApiClient appApiClient;
    @Autowired
    private CacheManage cacheManage;

    @Override
    public void getAllApi() {
        this.log.info("get All API for backend start ...");
        List<ApiEntry> appEntryList = appApiClient.getApi();
        ConcurrentHashMap<Integer, ApiEntry> map = new ConcurrentHashMap<>();
        for (ApiEntry api : appEntryList) {
            map.put(api.getId(), api);
        }
        cacheManage.refreshAPI(map);
        this.log.info("get All API for backend end ...");
    }

    @Override
    public void getAllApp() {
        this.log.info("get All APP for backend start ...");
        List<AppEntry> appEntryList = appApiClient.getApp();
        ConcurrentHashMap<String, AppEntry> map = new ConcurrentHashMap<>();
        for (AppEntry app : appEntryList) {
            map.put(app.getAppId(), app);
        }
        cacheManage.refreshAPP(map);
        this.log.info("get APP API for backend end ...");
    }

    @Override
    public void getAllAppApi() {
        log.info("get all appapi from backend start...");
        List<AppApiEntry> appapis = appApiClient.getAppApis();
        ConcurrentHashMap<String, AppApiEntry> map = new ConcurrentHashMap<>();

        for (AppApiEntry appApi : appapis) {
            String appid = appApi.getAppId();
            Integer apiid = appApi.getApiId();
            String key = appid + "-" + apiid;
            map.put(key, appApi);
        }

        cacheManage.refreshAppApi(map);
        this.log.info("get appapi from backend end ...");
    }

    @Override
    public void refreshCache() {
        /**
         * 刷新API
         */
        log.info("refresh api start ...");
        this.getAllApi();
        log.info("refresh api end ...");
        /**
         * 刷新APP
         */
        log.info("refresh app start ...");
        this.getAllApp();
        log.info("refresh app end ...");

        /**
         * 刷新URIEhiteList
         */
        log.info("refresh uriWhiteList start ...");
        this.getUriWhiteList();
        log.info("refresh uriWhiteList end ...");

        /**
         * 刷新appapi
         */
        log.info("refresh appapi start...");
        this.getAllAppApi();
        log.info("refresh appapi end...");
    }

    @Override
    public void getUriWhiteList() {
        this.log.info("get All UriWhiteList for backend start ...");
        List<String> list = appApiClient.getUriWhiteList();

        cacheManage.refreshURLWhiteList(list);
        this.log.info("get APP UriWhiteList for backend end ...");
    }

    @Override
    public String AppApiAuth(AppEntry app, ApiEntry api) {

        log.info("验证app的api权限开始...");
        Map<String, AppApiEntry> map = cacheManage.getAppApiList();
        if (null == map || map.size() == 0) {
            log.warn("app的api权限列表为空...");
            throw new RomeException(CodeMessage.APPAPISNOTEXIST.getCode(), CodeMessage.APPAPISNOTEXIST.getMessage());
        }

        String key = app.getAppId() + "-" + api.getId();

        AppApiEntry appApi = map.get(key);
        if (null == appApi) {
            log.warn("app的权限不存在...");
            throw new RomeException(CodeMessage.APIFORBIDDEN.getCode(), CodeMessage.APIFORBIDDEN.getMessage());
        }

        return appApi.getSelectField();
    }

}
