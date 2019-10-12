/*
 * Filename AuthCacheFreshAspect.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.common.aspect;

import com.rome.arch.eventbus.EventService;
import com.rome.openapi.backend.service.impl.AuthCacheFreshService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Random;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/8/9
 */
@Slf4j
@Aspect
@Component
public class AuthCacheFreshAspect {
    //	@Autowired
//	AuthCacheFreshService authCacheFreshService;
    @Autowired
    private EventService eventService;

//    @Autowired
//    private RefleshProducer refleshProducer;

    @AfterReturning("@annotation(com.rome.openapi.backend.common.annotation.AuthCacheFresh)")
    public void around() {
//		authCacheFreshService.cacheFresh();
        //增加缓存更新MQ modify by ken
        log.info("刷新配置MQ开始");
        eventService.sendEvent("authRefresh", new HashMap() {{
            put("authRefresh", new Random().nextLong());
        }});

//        refleshProducer.refleshApi(String.valueOf(new Random().nextLong()));
        log.info("刷新配置MQ结束");
    }
}
