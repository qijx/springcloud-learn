package com.rome.openapi.gateway.filter;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.rome.arch.core.clientobject.Response;
import com.rome.arch.core.exception.RomeException;
import com.rome.openapi.gateway.constant.OpenConatant;
import com.rome.openapi.gateway.dto.SignatureVO;
import com.rome.openapi.gateway.request.HttpServletRequestBodyWrapper;
import com.rome.openapi.gateway.service.ValidateTemplate;
import com.rome.openapi.gateway.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVICE_ID_KEY;

/**
 * @Class: SignFilter
 * @Auther: MarsKen
 * @Date: 2019/6/30 18:54
 * @Description:
 */
@Slf4j
public class SignFilter extends ZuulFilter {

    @Autowired
    private List<ValidateTemplate> validateTemplates;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return !ctx.containsKey(FilterConstants.FORWARD_TO_KEY) // a filter has already forwarded
                && !ctx.containsKey(SERVICE_ID_KEY) // a filter has already determined serviceId
                && !ctx.containsKey(OpenConatant.IS_STATIC_SOURCE) // 是否是一个静态资源
                && !(Boolean) ctx.get(OpenConatant.INNER_TO_KEY)//是否内部网关
                && !(Boolean) ctx.get(OpenConatant.WRITE_URLTO_KEY)//是否白名单
                ;
//                && !ctx.containsKey(OpenConatant.HAS_URI_WHITELIST);//如果存在url白名单，则不需要验签
    }

    @Override
    public Object run() throws ZuulException {
        log.info("进入签名验证拦截器...");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            HttpServletRequestBodyWrapper requestWrapper = new HttpServletRequestBodyWrapper(request);
            SignatureVO signatureVO = RequestUtil.buildSignatureDTO(requestWrapper);
            Boolean isInner = (Boolean) ctx.get(OpenConatant.INNER_TO_KEY);
            signatureVO.setInnerGateway(isInner);
            validateTemplates.forEach(validateTemplate -> validateTemplate.doValidate(signatureVO));
        } catch (RomeException e) {
            ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            Gson gson = new Gson();
            ctx.setResponseBody(gson.toJson(Response.builderFail(e.getCode(), e.getMessage())));
            ctx.setSendZuulResponse(false);
        }
        return null;
    }
}
