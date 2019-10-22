package com.example.scgateway.fileter;

import com.example.scgateway.exception.JSONStyleException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author qijx
 * @date 2019-10-21 19:31
 */
@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
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
        Throwable throwable = ctx.getThrowable().getCause();
        JSONStyleException exception = (JSONStyleException) throwable;
        if (!ctx.getResponse().isCommitted()){
            ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ctx.setResponseStatusCode(HttpStatus.OK.value());
            ctx.setResponseBody(exception.toString());
            try {
                ctx.getResponse().getWriter().append(exception.toString());
            } catch (IOException e) {
                log.error("写入信息错误",e);
            }
            ctx.set("sendErrorFilter.ran", true);
        }
        log.info("ErrorFilter->run:{}",ctx);
        return null;
    }
}
