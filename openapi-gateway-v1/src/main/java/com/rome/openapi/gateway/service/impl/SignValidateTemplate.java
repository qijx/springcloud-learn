package com.rome.openapi.gateway.service.impl;

import com.netflix.zuul.context.RequestContext;
import com.rome.arch.core.exception.RomeException;
import com.rome.openapi.gateway.constant.CodeMessage;
import com.rome.openapi.gateway.domain.entity.ApiEntry;
import com.rome.openapi.gateway.domain.entity.AppEntry;
import com.rome.openapi.gateway.dto.SignatureVO;
import com.rome.openapi.gateway.service.ApiService;
import com.rome.openapi.gateway.service.AppApiService;
import com.rome.openapi.gateway.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

/**
 * @Class: SiginValidateTemplate
 * @Auther: MarsKen
 * @Date: 2019/6/30 10:23
 * @Description:
 */
@Service(value = "signValidateTemplate")
@Order(3)
@Slf4j
public class SignValidateTemplate extends AbstractValidateTemplate {
    @Autowired
    private Validator validator;

    @Autowired
    private ApiService apiService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AppApiService appApiService;

    @Override
    public void beforValidate(SignatureVO signatureVO) {
        Set<ConstraintViolation<SignatureVO>> set = validator.validate(signatureVO);
        if (!set.isEmpty()) {
            throw new RomeException(CodeMessage.VALIDMESSAGE.getCode(), set.iterator().next().getMessage());
        }
    }

    @Override
    public void validate(SignatureVO signatureVO) {
        AppEntry app = apiService.appAuth(signatureVO.getClientId(), signatureVO.getRequestIP());
        Optional.ofNullable(app).orElseThrow(() -> new RomeException(CodeMessage.VALIDMESSAGE.getCode(), CodeMessage.VALIDMESSAGE.getMessage()));
        if (!signatureVO.isInnerGateway()) {
            authService.authTimeStamp(signatureVO.getTimeStamp());
        }
        ApiEntry api = apiService.apiAuth(signatureVO.getRequestURI(), signatureVO.getRequestMethod());

        String extend = appApiService.AppApiAuth(app, api);
        if (!signatureVO.isInnerGateway()) {
            authService.validSign(signatureVO);
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("extend", extend);

    }

    @Override
    public void afterValidate(SignatureVO signatureVO) {
    }
}
