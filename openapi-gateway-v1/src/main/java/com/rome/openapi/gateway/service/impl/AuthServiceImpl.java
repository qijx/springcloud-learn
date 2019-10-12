/*
 * Filename AuthServiceImpl.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.service.impl;

import com.rome.arch.core.exception.RomeException;
import com.rome.openapi.gateway.constant.CacheManage;
import com.rome.openapi.gateway.constant.CodeMessage;
import com.rome.openapi.gateway.domain.entity.AppEntry;
import com.rome.openapi.gateway.dto.SignatureVO;
import com.rome.openapi.gateway.service.AuthService;
import com.rome.openapi.gateway.utils.signature.SignCalculaterV1;
import com.rome.openapi.gateway.utils.signature.SignCalculaterV2;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/8/1
 */
@Service
public class AuthServiceImpl implements AuthService {
    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    //时间戳格式
    //有效期，单位: ms
    private static final long EFFECTIVE_TIME = 10 * 60 * 1000L;
    //签名1.0版本
    private static final Integer SIGN_VERSION_V1 = 1;
    //签名2.0版本
    private static final Integer SIGN_VERSION_V2 = 2;

    @Autowired
    private CacheManage cacheManage;

    @Override
    public boolean validURLWhiteList(String uri) {
        log.info("validURLWhiteList start ... {}", uri);
        List<String> uris = cacheManage.getURLWhiteList();
        for (String path : uris) {
//			path = path.replaceAll("\\{\\w+\\}", "\\\\w+");
            if (uri.matches(path)) {
                log.info("validURLWhiteList success ...");
                return true;
            }
        }
        log.info("validURLWhiteList fail ...");
        return false;
    }

    @Override
    public void validSign(SignatureVO signatureVO) {
        log.info("validSign start ... {}", signatureVO);

        AppEntry app = cacheManage.getAppList().get(signatureVO.getClientId());
        Optional.ofNullable(app).orElseThrow(() -> new RomeException(CodeMessage.SIGN_FAIL.getCode(), CodeMessage.SIGN_FAIL.getMessage()));

        String sign = "";
        log.info("client{} sign with the {} version", app.getAppName(), app.getSignVersion());
        if (SIGN_VERSION_V1.equals(app.getSignVersion())) {
            sign = SignCalculaterV1.calcuteSign(signatureVO, app);
        } else if (SIGN_VERSION_V2.equals(app.getSignVersion())) {
            sign = SignCalculaterV2.calcuteSign(signatureVO, app);
        }

        log.info("计算签名结果：{}，入参签名：{}", sign, signatureVO.getSign());
        if (!StringUtils.equals(signatureVO.getSign(), sign)) {
            log.warn("签名戳验证不通过...");
            throw new RomeException(CodeMessage.SIGN_FAIL.getCode(), CodeMessage.SIGN_FAIL.getMessage());
        }

    }

    @Override
    public void authTimeStamp(String timeStamp) {
        log.info("校验时间戳有效性，入参：{}", timeStamp);
        if (StringUtils.isEmpty(timeStamp)) {
            log.info("时间戳为空，返回失败");
            throw new RomeException(CodeMessage.VALIDMESSAGE.getCode(), CodeMessage.TIME_FAIL.getMessage());
        }
        Long timeStampMillis;
        try {
            timeStampMillis = Long.valueOf(timeStamp);
        } catch (Exception e) {
            log.info("时间戳格式错误，返回失败", e);
            throw new RomeException(CodeMessage.VALIDMESSAGE.getCode(), CodeMessage.TIME_FAIL.getMessage());
        }
        long diffMills = Math.abs(System.currentTimeMillis() - timeStampMillis);
        if (diffMills < EFFECTIVE_TIME) {
            log.info("时间戳有效性验证通过...");
        } else {
            log.info("时间戳有效性验证不通过...");
            throw new RomeException(CodeMessage.VALIDMESSAGE.getCode(), CodeMessage.TIME_FAIL.getMessage());
        }
    }
}
