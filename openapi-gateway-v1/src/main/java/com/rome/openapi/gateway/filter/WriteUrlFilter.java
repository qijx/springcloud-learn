package com.rome.openapi.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.rome.openapi.gateway.constant.OpenConatant;
import com.rome.openapi.gateway.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVICE_ID_KEY;

/**
 * @Class: WriteUrlFilter
 * @Auther: MarsKen
 * @Date: 2019/8/2 15:37
 * @Description:
 */
@Slf4j
public class WriteUrlFilter extends ZuulFilter {

    @Autowired
    private AuthService authService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return !ctx.containsKey(FilterConstants.FORWARD_TO_KEY) // a filter has already forwarded
                && !ctx.containsKey(SERVICE_ID_KEY) // a filter has already determined serviceId
                && !ctx.containsKey(OpenConatant.IS_STATIC_SOURCE)// 是否是一个静态资源
                ;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("进入白名单验证拦截器...");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Boolean hasWriteUri = authService.validURLWhiteList(request.getRequestURI());
        if (hasWriteUri) {
            ctx.set(OpenConatant.WRITE_URLTO_KEY, hasWriteUri);
        } else {
            ctx.set(OpenConatant.WRITE_URLTO_KEY, hasWriteUri);
        }
        return null;
    }
}
