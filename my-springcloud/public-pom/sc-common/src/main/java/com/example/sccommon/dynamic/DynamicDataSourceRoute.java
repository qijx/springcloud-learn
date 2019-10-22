package com.example.sccommon.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author qijx
 * @date 2019-10-12 15:57
 */


public class DynamicDataSourceRoute extends AbstractRoutingDataSource {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceRoute.class);

    public DynamicDataSourceRoute() {
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceKey = DynamicDataSourceContextHolder.getDataSource() == null ? DynamicDataSourceEnum.WRITE.name() : DynamicDataSourceContextHolder.getDataSource();
        log.debug("数据源为{}", dataSourceKey);
        return dataSourceKey;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }
}
