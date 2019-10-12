package com.rome.openapi.gateway.filter;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.rome.arch.core.clientobject.Response;
import com.rome.arch.core.exception.RomeException;
import com.rome.openapi.gateway.constant.CodeMessage;
import com.rome.openapi.gateway.constant.GatewayType;
import com.rome.openapi.gateway.constant.OpenConatant;
import com.rome.openapi.gateway.domain.entity.AppEntry;
import com.rome.openapi.gateway.service.ApiService;
import com.rome.openapi.gateway.utils.IpAdrressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVICE_ID_KEY;

/**
 * @Class: InnerFilter
 * @Auther: MarsKen
 * @Date: 2019/6/30 19:26
 * @Description:
 */
@Slf4j
public class InnerFilter extends ZuulFilter {


    @Autowired
    GatewayType gatewayType;

    @Autowired
    private ApiService apiService;


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set(OpenConatant.INNER_TO_KEY, Boolean.FALSE);
        return !ctx.containsKey(FilterConstants.FORWARD_TO_KEY) // a filter has already forwarded
                && !ctx.containsKey(SERVICE_ID_KEY) // a filter has already determined serviceId
                && !ctx.containsKey(OpenConatant.IS_STATIC_SOURCE) // 是否是一个静态资源
                && !(Boolean) ctx.get(OpenConatant.WRITE_URLTO_KEY)
                ;//是否白名单
    }

    @Override
    public Object run() throws ZuulException {
        log.info("进入内部网关验证拦截器...");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (gatewayType.getInner()) {
            ctx.set(OpenConatant.INNER_TO_KEY, Boolean.TRUE);
            try {
//                HttpServletRequestBodyWrapper requestWrapper = new HttpServletRequestBodyWrapper(request);
//                SignatureVO signatureVO = RequestUtil.buildSignatureDTO(requestWrapper);
                String clientId = request.getParameter(OpenConatant.PARAMETER_CLIENT) != null ?
                        request.getParameter(OpenConatant.PARAMETER_CLIENT) : request.getHeader(OpenConatant.HEADER_CLIENT);
                String requestIp = IpAdrressUtil.getIpAdrress(request);
                Optional.ofNullable(clientId).orElseThrow(() -> new RomeException(CodeMessage.VALIDMESSAGE.getCode(), "clientId不能为空"));
                AppEntry app = apiService.appAuth(clientId, requestIp);
                Optional.ofNullable(app).orElseThrow(() -> new RomeException(CodeMessage.VALIDMESSAGE.getCode(), CodeMessage.VALIDMESSAGE.getMessage()));
                ctx.set("app", app);
            } catch (RomeException e) {
                ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                Gson gson = new Gson();
                ctx.setResponseBody(gson.toJson(Response.builderFail(e.getCode(), e.getMessage())));
                ctx.setSendZuulResponse(false);
            }
        }
        return null;
    }

}
