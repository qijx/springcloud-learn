package com.example.sccommon.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qijx
 * @date 2019-10-12 15:56
 */


public class DynamicDataSourceContextHolder {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);
    private static final ThreadLocal<String> contextHolder = new ThreadLocal();
    public static List<String> dataSourceIds = new ArrayList();

    public DynamicDataSourceContextHolder() {
    }

    public static void setDataSource(String dataSource) {
        log.info("切换到[{}]数据源", dataSource);
        contextHolder.set(dataSource);
    }

    public static String getDataSource() {
        return (String)contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }

    public static boolean containsDataSource(String dataSourceType) {
        return dataSourceIds.contains(dataSourceType);
    }
}
