package com.rome.openapi.gateway.utils.signature;

import com.rome.openapi.gateway.domain.entity.AppEntry;
import com.rome.openapi.gateway.dto.SignatureVO;
import com.rome.openapi.gateway.utils.encrypt.MacEncoder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class SignCalculaterV2 {

    private static Logger logger = LoggerFactory.getLogger(SignCalculaterV2.class);

    private static final String HEADER_CLIENT = "X-Co-Client";
    private static final String HEADER_TIMESTAMP = "X-Co-TimeStamp";

    private static final String EQUWAL_SYMBOL = "=";
    private static final String AND_SYMBOL = "&";
    private static final String COLON_SYMBOL = ":";
    private static final String ENTER_SYMBOL = "\n";

    public static String calcuteSign(SignatureVO vo, AppEntry app) {

        String queryString = canonicalQueryString(vo.getParameter());
        logger.info("queryString:{}", queryString);
        String headerString = canonicalHeader(vo.getHeaders());
        logger.info("headerString:{}", headerString);

        StringBuilder encoderData = new StringBuilder();
        encoderData.append(vo.getRequestMethod());
        encoderData.append(ENTER_SYMBOL);
        encoderData.append(vo.getRequestURI());

        if (StringUtils.isNotEmpty(queryString)) {
            encoderData.append(ENTER_SYMBOL);
            encoderData.append(queryString);
        }

        encoderData.append(ENTER_SYMBOL);
        encoderData.append(headerString);

        if (StringUtils.isNotEmpty(vo.getMd5Body())) {
            encoderData.append(ENTER_SYMBOL);
            encoderData.append(vo.getMd5Body());
        }

        String secretKey = app.getSecretKey();
        logger.info("参与加密的字符串：{}", encoderData);
        byte[] hamcSign = new byte[0];
        //之后可以改城按照不同的加密规则加密
        try {
            hamcSign = MacEncoder.encodeHmacSHAbyte(encoderData.toString().getBytes(), secretKey.getBytes());
        } catch (Exception e) {
            logger.error("HMAC-SHA1加密异常", e);
        }

        Base64.Encoder encoder = Base64.getEncoder();

        String sign = encoder.encodeToString(hamcSign);

        return sign;
    }

    private static String canonicalQueryString(Map<String, Object> parameter) {
        if (null != parameter && parameter.size() > 0) {
            String[] keys = parameter.keySet().toArray(new String[0]);
            Arrays.sort(keys);

            StringBuilder sb = new StringBuilder();

            for (String key : keys) {

                String value = String.valueOf(parameter.get(key));

                if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                    sb.append(AND_SYMBOL);
                    sb.append(key);
                    sb.append(EQUWAL_SYMBOL);
                    sb.append(URLEncoder.encode(value));
                }
            }

            String result = sb.toString().substring(1);

            return result;
        }

        return null;
    }

    private static String canonicalHeader(Map<String, Object> headers) {

        if (null != headers && headers.size() > 0) {
            String[] keys = headers.keySet().toArray(new String[0]);
            Arrays.sort(keys);

            StringBuilder sb = new StringBuilder();

            for (String key : keys) {

                String value = String.valueOf(headers.get(key));

                if (StringUtils.isNotEmpty(key)) {
                    sb.append(ENTER_SYMBOL);
                    sb.append(StringUtils.lowerCase(key));
                    sb.append(COLON_SYMBOL);
                    sb.append(StringUtils.trim(value));
                }
            }

            String result = sb.toString().substring(1);

            return result;
        }

        return null;

    }

}
