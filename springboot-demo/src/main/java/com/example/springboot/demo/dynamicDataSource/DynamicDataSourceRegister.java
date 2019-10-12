package com.example.springboot.demo.dynamicDataSource;//package com.example.springcloud.client.dynamicDataSource;
//
///**
// * @author qijx
// * @date 2019-07-04 11:54
// */
//
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import javax.sql.DataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.MutablePropertyValues;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.env.Environment;
//import org.springframework.core.type.AnnotationMetadata;
//
//public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
//    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceRegister.class);
//    private static final String DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";
//    private DataSource defaultDataSource;
//    private Map<DynamicDataSourceEnum, DataSource> slaveDataSources = new HashMap();
//
//    public DynamicDataSourceRegister() {
//    }
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.initDefaultDataSource(environment);
//        this.initslaveDataSources(environment);
//    }
//
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
//        Map<String, Object> defaultAttrs = annotationMetadata.getAnnotationAttributes(EnableDynamicDataSource.class.getName(), true);
//        if (defaultAttrs != null) {
//            Map<Object, Object> targetDataSources = new HashMap();
//            targetDataSources.put("dataSource", this.defaultDataSource);
//            DynamicDataSourceContextHolder.dataSourceIds.add(DynamicDataSourceEnum.WRITE.name());
//            targetDataSources.putAll(this.slaveDataSources);
//            Iterator var5 = this.slaveDataSources.keySet().iterator();
//
//            while(var5.hasNext()) {
//                DynamicDataSourceEnum dynamicDataSourceEnum = (DynamicDataSourceEnum)var5.next();
//                DynamicDataSourceContextHolder.dataSourceIds.add(dynamicDataSourceEnum.name());
//            }
//
//            if (this.defaultDataSource != null) {
//                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
//                beanDefinition.setBeanClass(DynamicDataSourceRoute.class);
//                beanDefinition.setSynthetic(true);
//                MutablePropertyValues mpv = beanDefinition.getPropertyValues();
//                mpv.addPropertyValue("defaultTargetDataSource", this.defaultDataSource);
//                mpv.addPropertyValue("targetDataSources", targetDataSources);
//                beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
//            }
//
//            log.info("Dynamic DataSource Registry");
//        }
//
//    }
//
//    private void initDefaultDataSource(Environment env) {
//        Map<String, Object> dsMap = new HashMap();
//        dsMap.put("type", env.getProperty("spring.datasource.type"));
//        dsMap.put("driver", env.getProperty("spring.datasource.driver-class-name"));
//        dsMap.put("url", env.getProperty("spring.datasource.url"));
//        dsMap.put("username", env.getProperty("spring.datasource.username"));
//        dsMap.put("password", env.getProperty("spring.datasource.password"));
//        this.defaultDataSource = this.buildDataSource(dsMap);
//    }
//
//    private void initslaveDataSources(Environment env) {
//        String dsPrefixs = env.getProperty("slave.datasource.names");
//        if (dsPrefixs != null) {
//            String[] var3 = dsPrefixs.split(",");
//            int var4 = var3.length;
//
//            for(int var5 = 0; var5 < var4; ++var5) {
//                String dsPrefix = var3[var5];
//                Map<String, Object> dsMap = new HashMap();
//                dsMap.put("type", env.getProperty("slave.datasource." + dsPrefix + ".type"));
//                dsMap.put("driver", env.getProperty("slave.datasource." + dsPrefix + ".driver-class-name"));
//                dsMap.put("url", env.getProperty("slave.datasource." + dsPrefix + ".url"));
//                dsMap.put("username", env.getProperty("slave.datasource." + dsPrefix + ".username"));
//                dsMap.put("password", env.getProperty("slave.datasource." + dsPrefix + ".password"));
//                DataSource ds = this.buildDataSource(dsMap);
//                this.slaveDataSources.put(DynamicDataSourceEnum.READ, ds);
//            }
//        }
//
//    }
//
//    public DataSource buildDataSource(Map<String, Object> dataSourceMap) {
//        try {
//            Object type = dataSourceMap.get("type");
//            if (type == null) {
//                type = "com.zaxxer.hikari.HikariDataSource";
//            }
//
//            Class<? extends DataSource> dataSourceType =  (Class<? extends DataSource>)Class.forName((String)type);
//            String driverClassName = dataSourceMap.get("driver").toString();
//            String url = dataSourceMap.get("url").toString();
//            String username = dataSourceMap.get("username").toString();
//            String password = dataSourceMap.get("password").toString();
//            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).type(dataSourceType);
//            return factory.build();
//        } catch (ClassNotFoundException var9) {
//            log.error("ClassNotFoundException", var9);
//            return null;
//        }
//    }
//}
