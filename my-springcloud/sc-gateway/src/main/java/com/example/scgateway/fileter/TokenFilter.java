package com.example.scgateway.fileter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qijx
 * @date 2019-10-22 10:15
 */
@Slf4j
@Component
public class TokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {

        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String uri = request.getRequestURI();
        //过滤请求swagger文档接口,不验证token
        if (uri.contains("/v2/api-docs")){
            return null;
        }
        System.out.println(uri);
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)){
            //不会继续执行其他的拦截器
            context.setSendZuulResponse(false);
            context.setResponseBody("token is null");
            context.setResponseStatusCode(401);
            return null;
        }
        log.info("token is  not null");
        return null;
    }
}
