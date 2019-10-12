/*
 * Filename ResponseBodyFilter.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.rome.arch.core.clientobject.Response;
import com.rome.openapi.gateway.constant.OpenConatant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

/**
 * 如果用户指定返回字段，过滤用户字段
 * @author kongweixiang
 * @since 1.0.0_2018/8/1
 */
@Slf4j
public class ResponseBodyFilter extends ZuulFilter {
//	private final Logger log = LoggerFactory.getLogger(ResponseBodyFilter.class);
	private static final String SUCCESS = "000000";
	private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	@Override
	public String filterType() {
		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {
//		return SEND_RESPONSE_FILTER_ORDER - 1;
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return !ctx.containsKey(OpenConatant.IS_STATIC_SOURCE)
				&& ctx.getThrowable() == null;
	}

	@Override
	public Object run() throws ZuulException {
		log.info("进入返回字段过滤拦截器...");
		try {
			RequestContext context = RequestContext.getCurrentContext();
			//判断是否有选择返回字段
			String extendStr = (String) context.get("extend");
			if (StringUtils.isBlank(extendStr)) {
				return null;
			}
//           //SEND_RESPONSE_FILTER_ORDER 之后
//			HttpServletResponse response = context.getResponse();
//			String contentType = response.getContentType();
			String contentType = "";
			Object zuulResponse = RequestContext.getCurrentContext().get("zuulResponse");
			if (zuulResponse != null) {
				RibbonHttpResponse resp = (RibbonHttpResponse) zuulResponse;
				contentType = resp.getHeaders().getContentType().toString();
			}
			int status = context.getResponse().getStatus();
			List<String> extend = new ArrayList<>();
			for (String ex : extendStr.split(",")) {
				extend.add(ex);
			}
			if((contentType.equals(MediaType.APPLICATION_JSON_VALUE)|| contentType.equals(MediaType.APPLICATION_JSON_UTF8_VALUE))
					&& status==HttpStatus.OK.value() && extend.size()>0) {

				InputStream stream = context.getResponseDataStream();
				String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
				if (StringUtils.isBlank(body)) {
					return null;
				}
				try {
					Map<String,Object> result = gson.fromJson(body, Map.class);
					Map<String, Object> data = new HashMap<>();
					Map<String, Object> newResult = new HashMap<>();
					if (SUCCESS.equals(result.get("code"))) {
						try {
							data = (Map<String, Object>) result.get("data");
						} catch (Exception e) {
							return null;
						}
					}
					for(String key : extend) {
						Object value = data.get(key);
						newResult.put(key, value);
					}
					if (newResult.size() > 0) {
						result.put("data", newResult);
						body = gson.toJson(result);
						context.setResponseBody(body);
					}
				} catch (Exception e) {
					log.error("response field error",e);
					context.setResponseStatusCode(500);
					Gson gson = new Gson();
					context.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					context.setResponseBody(gson.toJson(Response.builderFail("400999","result json error")));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
