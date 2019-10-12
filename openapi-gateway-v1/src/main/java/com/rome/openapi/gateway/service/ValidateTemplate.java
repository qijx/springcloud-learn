package com.rome.openapi.gateway.service;

import com.rome.openapi.gateway.dto.SignatureVO;

/**
 * @Class: ValidateTemplate
 * @Auther: MarsKen
 * @Date: 2019/6/30 10:34
 * @Description:
 */
public interface ValidateTemplate {
    void doValidate(SignatureVO signatureVO);
}
