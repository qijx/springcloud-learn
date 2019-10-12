//package com.laiyifen.openapi.backend.job;
//
//import com.laiyifen.openapi.backend.service.OpenApiGroupService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * @Class: RegisteApiTask
// * @Auther: MarsKen
// * @Date: 2019/5/5 23:41
// * @Description:
// */
//@Component
//@EnableScheduling
//public class RegisteApiTask {
//
//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//    @Autowired
//    private OpenApiGroupService openApiGroupService;
//
//    /**
//     * 每15分钟进行从注册中心中获取服务id
//     * 替换原有的com.laiyifen.openapi.backend.controller.OpenApiController#register(java.lang.String)接口
//     */
//    @Scheduled(cron = "0 */15 *  * * * ")
//    public void registeApi() {
//        discoveryClient.getServices().forEach(service -> {
//            discoveryClient.getInstances(service).forEach(serviceInstance -> {
//                openApiGroupService.register(serviceInstance.getServiceId());
//            });
//        });
//    }
//
//}
