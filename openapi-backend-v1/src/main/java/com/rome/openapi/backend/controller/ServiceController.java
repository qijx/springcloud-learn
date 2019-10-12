/*
 * Filename OpenApiController.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rome.openapi.backend.common.Query;
import com.rome.openapi.backend.service.OpenApiGroupService;
import com.rome.openapi.backend.service.SwaggerResolveApiService;
import com.rome.openapi.backend.util.R;
import com.rome.openapi.backend.util.page.PageUtils;
import com.rome.openapi.backend.vo.OpenApiGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * service服务管理
 *
 * openApi 注册服务controller
 *
 * @author kongweixiang
 * @since 1.0.0_2018/8/8
 */
@Controller
@RequestMapping("/openApi")
public class ServiceController {
    @Autowired
    OpenApiGroupService openApiGroupService;

    @Autowired
    SwaggerResolveApiService SwaggerResolveApiService;

    @Autowired
    private DiscoveryClient discoveryClient;



    /**
     * 微服务列表查询
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public PageUtils list(@RequestBody Map<String, Object> params) {
        /**
         * 从注册中心上拉取所有已经注册的服务并进行存库操作
         */
        //获取所有服务实例
        discoveryClient.getServices().forEach(service -> {
            //根据服务id获取该服务的详情
            discoveryClient.getInstances(service).forEach(serviceInstance -> {
                //把从注册中心拉取到的service进行存库操作
                System.out.println("服务的名称"+serviceInstance.getServiceId());
                openApiGroupService.register(serviceInstance.getServiceId());
            });
        });

        //存完数据库之后再从数据库进行分页查询
        Query query = new Query(params);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<OpenApiGroup> openAppList = this.openApiGroupService.list(params);
        PageInfo<OpenApiGroup> page = new PageInfo<>(openAppList);
        return new PageUtils(page);
    }

    /**
     * 点击服务名（id）注册api，调用swagger的接口文档加载该server服务下的接口文档
     */
    @PostMapping("/register")
    @ResponseBody
    public R remove(Integer serviceId) {
        this.SwaggerResolveApiService.registerAPI(serviceId);
        return R.ok();
    }




    @GetMapping()
    String Api() {
        return "apiRegister/apiRegister";
    }

    @ResponseBody
    @GetMapping("/register/{serviceId}")
    public String register(@PathVariable("serviceId") String serviceId) {
        this.openApiGroupService.register(serviceId);
        return "success";
    }



    @ResponseBody
    @GetMapping("/show/{serviceId}")
    public String show(@PathVariable("serviceId") String serviceId) {
        this.openApiGroupService.register(serviceId);
        return "success";
    }





    @PostMapping("/serviceRemove")
    @ResponseBody
    public R serviceRemove(Integer serviceId) {
        this.openApiGroupService.remove(serviceId);
        return R.ok();
    }

}
