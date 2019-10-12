/*
 * Filename CacheManage.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.constant;

import com.rome.openapi.gateway.domain.entity.ApiEntry;
import com.rome.openapi.gateway.domain.entity.AppApiEntry;
import com.rome.openapi.gateway.domain.entity.AppEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 存放系统缓存内容
 * api及api权限
 *
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
@Getter
@Setter
public class CacheManage {
    private Map<String, AppEntry> appList = new ConcurrentHashMap<>();
    private Map<Integer, ApiEntry> apiList = new ConcurrentHashMap<>();
    private Map<String, AppApiEntry> appApiList = new ConcurrentHashMap<>();
    private CopyOnWriteArrayList<String> URLWhiteList = new CopyOnWriteArrayList<>();
    private static volatile Lock lock = new ReentrantLock();
    private volatile static CacheManage instance = null;

    private CacheManage() {
    }

    public static CacheManage getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new CacheManage();
            }
            lock.unlock();
        }
        return instance;
    }


    public void refreshAPI(Map<Integer, ApiEntry> map) {
        ConcurrentHashMap<Integer, ApiEntry> temp = new ConcurrentHashMap<>(map);
        synchronized (apiList) {
            apiList = temp;
        }
    }

    public void refreshAPP(Map<String, AppEntry> map) {
        ConcurrentHashMap<String, AppEntry> temp = new ConcurrentHashMap(map);
        synchronized (apiList) {
            appList = temp;
        }
    }

    public void refreshAppApi(Map<String, AppApiEntry> map) {
        ConcurrentHashMap<String, AppApiEntry> temp = new ConcurrentHashMap(map);
        synchronized (appApiList) {
            appApiList = temp;
        }
    }

    public void refreshURLWhiteList(List<String> list) {
        CopyOnWriteArrayList<String> temp = new CopyOnWriteArrayList<>(list);
        synchronized (URLWhiteList) {
            URLWhiteList = temp;
        }
    }

    public void destroy() {
        this.apiList.clear();
        this.appList.clear();
        this.URLWhiteList.clear();
        this.appApiList.clear();
    }
}
