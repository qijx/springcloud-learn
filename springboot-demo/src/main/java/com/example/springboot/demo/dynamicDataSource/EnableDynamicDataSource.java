package com.example.springboot.demo.dynamicDataSource;//package com.example.springcloud.client.dynamicDataSource;
//
///**
// * @author qijx
// * @date 2019-07-04 11:55
// */
//
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//import org.springframework.context.annotation.Import;
//
//@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.TYPE})
//@Documented
//@Import({DynamicDataSourceRegister.class})
//public @interface EnableDynamicDataSource {
//    /**
//     * 动态切换数据源，一定要在启动类上加开启切换数据源注解@EnableDynamicDataSource
//     */
//
//
//    /**
//     *数据源配置方式如下：（读写分离，一主多从形式）
//     *
//     spring:
//         datasource:
//             type: com.zaxxer.hikari.HikariDataSource
//             driver-class-name: com.mysql.cj.jdbc.Driver
//             url: jdbc:mysql://10.0.13.12:3306/pc?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true&serverTimezone=Asia/Shanghai
//             username: lyf
//             password: lyf#EDC
//             hikari:
//                 minimum-idle: 5
//                 maximum-pool-size: 1000
//                 auto-commit: true
//                 idle-timeout: 30000
//                 pool-name: DatebookHikariCP
//                 connection-timeout: 30000
//     slave:
//         datasource:
//             names: db1,db2
//             db1:
//                 type: com.zaxxer.hikari.HikariDataSource
//                 driver-class-name: com.mysql.cj.jdbc.Driver
//                 url: jdbc:mysql://10.0.13.12:3306/pc?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true&serverTimezone=UTC
//                 username: lyf
//                 password: lyf#EDC
//             db2:
//                 type: com.zaxxer.hikari.HikariDataSource
//                 driver-class-name: com.mysql.cj.jdbc.Driver
//                 url: jdbc:mysql://10.0.13.12:3306/pc?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true&serverTimezone=UTC
//                 username: lyf
//                 password: lyf#EDC
//     */
//
//
//}
