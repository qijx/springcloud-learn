package com.rome.openapi.gateway.service.impl;

import com.rome.arch.core.exception.RomeException;
import com.rome.openapi.gateway.constant.CodeMessage;
import com.rome.openapi.gateway.dto.SignatureVO;
import com.rome.openapi.gateway.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Class: HeaderValidateTemplate
 * @Auther: MarsKen
 * @Date: 2019/6/30 19:55
 * @Description:
 */
@Service(value = "headerValidateTemplate")
@Order(1)
@Slf4j
public class HeaderValidateTemplate extends AbstractValidateTemplate {
    @Autowired
    private AuthService authService;

    @Override
    public void beforValidate(SignatureVO signatureVO) {
        Optional.ofNullable(signatureVO).orElseThrow(() -> new RomeException(CodeMessage.VALIDMESSAGE.getCode(), "signatureVO不能为空"));

    }

    @Override
    public void validate(SignatureVO signatureVO) {

        Optional.ofNullable(signatureVO.getClientId()).orElseThrow(() -> new RomeException(CodeMessage.VALIDMESSAGE.getCode(), "clientId不能为空"));

    }

    @Override
    public void afterValidate(SignatureVO signatureVO) {
    }
}
