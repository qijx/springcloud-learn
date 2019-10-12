/*
 * Filename ApiErrorFilter.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.filter;

import com.rome.openapi.gateway.exception.JSONStyleException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 处理自定义抛出异常
 * @author kongweixiang
 * @since 1.0.0_2018/8/2
 */
@Component
public class ApiErrorFilter extends ZuulFilter {
	private final Logger log = LoggerFactory.getLogger(ApiErrorFilter.class);
	@Override
	public String filterType() {
		return FilterConstants.ERROR_TYPE;
	}

	@Override
	public int filterOrder() {
		return -1;//before SendErrorFilter
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return ctx.getThrowable() != null
				&& !ctx.getBoolean("sendErrorFilter.ran", false)
				&& (ctx.getThrowable().getCause() instanceof JSONStyleException);
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
		return null;
	}
}
