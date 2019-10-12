/*
 * Filename ApplicationStartup.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.config;

import com.rome.arch.eventbus.EventService;
import com.rome.openapi.gateway.service.AppApiService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
@Component
@Slf4j
public class ApplicationStartup implements CommandLineRunner {

    @Autowired
    private AppApiService appApiService;

    @Autowired
    private EventService eventService;

    @Override
    public void run(String... args) {
        log.info("加载系统配置...");
        log.info("system init app-api start...");
//		获取spring容器
        appApiService.refreshCache();
        try {
            eventService.bindEvent("openapi-backend","authRefresh",e -> {
                appApiService.refreshCache();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("system init app-api end...");
        log.info("系统配置加载完成...");
    }

}
