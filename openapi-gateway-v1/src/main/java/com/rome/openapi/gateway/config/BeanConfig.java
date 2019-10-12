/*
 * Filename BeanConfig.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.config;

import com.rome.openapi.gateway.constant.CacheManage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/30
 */
@Configuration
public class BeanConfig {

    @Bean(value = "cacheManage", destroyMethod = "destroy")
    public CacheManage getCacheManege() {
        return CacheManage.getInstance();
    }

}
