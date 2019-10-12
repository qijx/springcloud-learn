package com.example.springboot.demo.dynamicDataSource;//package com.example.springcloud.client.dynamicDataSource;
//
///**
// * @author qijx
// * @date 2019-07-04 11:54
// */
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//public class DynamicDataSourceRoute extends AbstractRoutingDataSource {
//    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceRoute.class);
//
//    public DynamicDataSourceRoute() {
//    }
//
//    @Override
//    protected Object determineCurrentLookupKey() {
//        String dataSourceKey = DynamicDataSourceContextHolder.getDataSource() == null ? DynamicDataSourceEnum.WRITE.name() : DynamicDataSourceContextHolder.getDataSource();
//        log.debug("数据源为{}", dataSourceKey);
//        return dataSourceKey;
//    }
//    @Override
//    public void afterPropertiesSet() {
//        super.afterPropertiesSet();
//    }
//}
