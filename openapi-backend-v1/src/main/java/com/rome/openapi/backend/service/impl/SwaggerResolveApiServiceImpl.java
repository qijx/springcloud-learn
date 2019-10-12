/*
 * Filename SwaggerResolveApiServiceImpl.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.rome.openapi.backend.service.OpenApiGroupService;
import com.rome.openapi.backend.service.OpenApiService;
import com.rome.openapi.backend.service.SwaggerResolveApiService;
import com.rome.openapi.backend.vo.OpenApi;
import com.rome.openapi.backend.vo.OpenApiGroup;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/8/3
 */
@Service
public class


SwaggerResolveApiServiceImpl implements SwaggerResolveApiService {
    private final Logger log = LoggerFactory.getLogger(SwaggerResolveApiServiceImpl.class);
    private final String OPENAPI = "openApi";
    protected volatile ReentrantLock lock = new ReentrantLock();//最好是分布式的资源锁[借助缓存实现]
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    OpenApiGroupService openApiGroupService;
    @Autowired
    OpenApiService openApiService;



    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void registerAPI(Integer serviceId) {
        log.info("swagger resolve service id {} start ...", serviceId);
        OpenApiGroup openApiGroup = openApiGroupService.get(serviceId);
        List<OpenApi> openApiList = this.swaggerResolveApi(openApiGroup.getService().toLowerCase());
        log.debug("get swagger api resolve list:{}", JSON.toJSONString(openApiList));
        lock.lock();
        try {
            this.pullAPI(openApiList, openApiGroup.getId());
        } finally {
            lock.unlock();
        }

    }

    /**
     * 注册具体Api的path
     * 现在是使用loadbalance负载均衡调用，看是否需要后期调整修改从ip地址调用获得path
     *
     * @param service
     * @return
     */
    public List<OpenApi> swaggerResolveApi(String service) {
        List<OpenApi> openApiList = new ArrayList<>();
        String serviceUrl = "http://" + service + "/v2/api-docs";
        log.info("swagger resolve service by url{}", serviceUrl);
        //负载均衡调用
        String json = restTemplate.getForObject(serviceUrl, String.class);
        Swagger swagger = new SwaggerParser().parse(json);

        Map<String, Path> pathMap = swagger.getPaths();

        for (String url : pathMap.keySet()) {
            Path path = pathMap.get(url);
            if (!path.isEmpty()) {
                Map<HttpMethod, Operation> operations = path.getOperationMap();
                for (HttpMethod method : operations.keySet()) {
                    Operation op = operations.get(method);
//					List<String> tags = op.getTags();
//					if (tags.contains(OPENAPI)) {
                    OpenApi openApi = new OpenApi();
                    openApi.setName(op.getSummary());
                    openApi.setMethod(method.name());
                    openApi.setPath("/" + service + url);
                    openApiList.add(openApi);
//					}
                }
            }
        }
        return openApiList;
    }

    public void pullAPI(List<OpenApi> openApis, int groupId) {
        //锁定后再次获取service信息
        List<OpenApi> addList = new ArrayList<>();
        List<OpenApi> updateList = new ArrayList<>();
        List<Integer> deleteList = new ArrayList<>();
        OpenApiGroup openApiGroup = openApiGroupService.get(groupId);
        if (openApiGroup.getAvailable() == 0) {//表示未注册过api
            openApiGroup.setAvailable(1);//修改注册状态
            this.openApiGroupService.update(openApiGroup);
            for (OpenApi api : openApis) {
                api.setGroupId(groupId);
            }
            addList.addAll(openApis);
        } else {
            //选获取该服务下数据库中所有api列表
            List<OpenApi> openApiList = this.openApiService.selectByGroupId(groupId);

            for (OpenApi api : openApis) {
                //如果数据库api包含注册api，则更新
                if (openApiList.contains(api)) {
                    OpenApi op = new OpenApi();
                    BeanUtils.copyProperties(api, op);
                    op.setId(api.getId());
                    updateList.add(op);
                } else {//如果数据库api不包含注册api，则新增
                    OpenApi op = new OpenApi();
                    BeanUtils.copyProperties(api, op);
                    op.setGroupId(groupId);
                    addList.add(op);
                }
            }

            //如果注册api不包含的数据库api，则删除
            for (OpenApi openApi : openApiList) {
                if (!openApis.contains(openApi)) {
                    deleteList.add(openApi.getId());
                }
            }
        }

        //操作数据库
        if (!addList.isEmpty()) {
            for (OpenApi api : addList) {
                this.openApiService.save(api);
            }
        }
        if (!updateList.isEmpty()) {
            for (OpenApi api : updateList) {
                this.openApiService.update(api);
            }
        }
        if (!deleteList.isEmpty()) {
            Integer[] ids = new Integer[]{};
            ids = deleteList.toArray(ids);
            this.openApiService.batchRemove(ids);
        }
    }

    @Override
    public String showApi(String serviceId, String group) {
        String serviceUrl = "http://" + serviceId + "/v2/api-docs";
        if (StringUtils.isNotBlank(group)) {
            serviceUrl = serviceUrl + "?group=" + group.trim();
        }
        log.info("swagger resolve service by url{}", serviceUrl);
        String apiDocs = restTemplate.getForObject(serviceUrl, String.class);
        return apiDocs;
    }

    @Override
    public List<SwaggerResource> getSwaggerResoure() {
        List<SwaggerResource> result = new ArrayList<>();
        List<OpenApiGroup> openApiGroup = openApiGroupService.listAll();
        for (OpenApiGroup apiGroup : openApiGroup) {
            SwaggerResource s = new SwaggerResource();
            s.setName(apiGroup.getService());
            s.setSwaggerVersion("1.0.0");
            s.setLocation("/showApi/" + apiGroup.getService());
            SwaggerResource s2 = new SwaggerResource();
            s2.setName(apiGroup.getService() + "-openApi");
            s2.setSwaggerVersion("1.0.0");
            s2.setLocation("/showApi/" + apiGroup.getService() + "/openApi");
            result.add(s);
            result.add(s2);
        }
        return result;
    }


}


