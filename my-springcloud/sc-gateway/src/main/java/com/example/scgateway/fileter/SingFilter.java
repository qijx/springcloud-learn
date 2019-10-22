package com.example.scgateway.fileter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author qijx
 * @date 2019-10-21 19:31
 */
@Slf4j
@Component
public class SingFilter extends ZuulFilter {
    @Override
    public String filterType() {
        /**
         * 路由类型：pre，请求路由前执行
         * route,请求路由时执行
         * error,路由出错时执行
         * post,路由之后执行
         */

        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        /**
         * int值代表优先级，越小优先级越大
         */
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        log.info("SingFilter->shouldFilter:{}",ctx);
        /**
         * true：代表执行该过滤器
         * false：不执行过滤器
         * 可以在这里判断该请求是都满足执行此run方法的条件
         */
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        log.info("SingFilter->run:{}",ctx);
        return null;
    }
}
