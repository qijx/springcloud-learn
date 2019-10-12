/*
 * Filename ApiJob.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.job;

import com.rome.openapi.gateway.service.AppApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时获取qpi和app信息
 *
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
@Component
public class ApiJob {
    private Logger log = LoggerFactory.getLogger(ApiJob.class);

    @Autowired
    private AppApiService appApiService;


    @Scheduled(cron = "0 0/15 * * *  ?")
    public void refreshCache() {
        log.info("auth api refreshCache by Scheduled");
        appApiService.refreshCache();
    }
}
