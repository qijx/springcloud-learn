package com.rome.openapi.gateway.service.impl;

import com.rome.openapi.gateway.dto.SignatureVO;
import com.rome.openapi.gateway.service.ValidateTemplate;
import org.springframework.stereotype.Service;

/**
 * @Class: ValidateTemplate
 * @Auther: MarsKen
 * @Date: 2019/6/30 09:49
 * @Description:
 */
@Service
public abstract class AbstractValidateTemplate implements ValidateTemplate {

    @Override
    public void doValidate(SignatureVO signatureVO) {
        beforValidate(signatureVO);
        validate(signatureVO);
        afterValidate(signatureVO);
    }

    public abstract void beforValidate(SignatureVO signatureVO);

    public abstract void validate(SignatureVO signatureVO);

    public abstract void afterValidate(SignatureVO signatureVO);

}
