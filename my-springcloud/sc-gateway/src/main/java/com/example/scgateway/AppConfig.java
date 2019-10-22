package com.example.scgateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过zuul网关整合所有服务的swagger
 */
@Primary
@Component
public class AppConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List resource = new ArrayList<>();
        resource.add(swaggerResource("sc-test-user01","/user01/v2/api-docs","2.0"));
        resource.add(swaggerResource("sc-test-user01","/user02/v2/api-docs","2.0"));
        return resource;
    }


    private SwaggerResource swaggerResource(String name, String location,String version){
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return  swaggerResource;
    }
}
