package com.rome.openapi.gateway.filter;

import com.rome.openapi.gateway.constant.OpenConatant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

public class StaticSourceFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String uri = request.getRequestURI();

        if (uri.contains(".js") || uri.contains(".css")
                || uri.contains("swagger")
                || uri.contains("api-docs")
                || uri.contains("webjars")){
            ctx.set(OpenConatant.IS_STATIC_SOURCE);
        }

        return null;
    }
}
