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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SignCalculaterV1 {

    private static Logger logger = LoggerFactory.getLogger(SignCalculaterV1.class);

    private static final String HEADER_CLIENT = "X-Co-Client";
    private static final String HEADER_TIMESTAMP = "X-Co-TimeStamp";

    private static final String EQUWAL_SYMBOL = "=";
    private static final String AND_SYMBOL = "&";
    private static final String COLON_SYMBOL = ":";
    private static final String ENTER_SYMBOL = "\n";

    public static String calcuteSign(SignatureVO vo, AppEntry app) {

        String queryString = canonicalQueryString(vo.getParameter());
        logger.info("queryString:{}", queryString);
        String headerString = canonicalHeader(vo.getClientId(), vo.getAccessToken(), vo.getTimeStamp(), vo.getFormat());
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

    private static String canonicalHeader(String clientId, String AccessToken, String timestamp, String format) {

        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotEmpty(clientId)) {
            sb.append(StringUtils.lowerCase(HEADER_CLIENT));
            sb.append(COLON_SYMBOL);
            sb.append(StringUtils.trim(clientId));
        }

        if (StringUtils.isNotEmpty(timestamp)) {
            sb.append(ENTER_SYMBOL);
            sb.append(StringUtils.lowerCase(HEADER_TIMESTAMP));
            sb.append(COLON_SYMBOL);
            sb.append(StringUtils.trim(timestamp));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        SignatureVO vo = new SignatureVO();
        vo.setRequestURI("/realtime-service/mobile/realtimeReport");
        vo.setTimeStamp("1548389041449");
        vo.setClientId("CFA89EC86C974828B2BB1D0E1F7E659C");
        vo.setRequestMethod("GET");
        Map map = new HashMap();
        map.put("ut", "6932f182917bb8973b39d54c74e9602441");
        map.put("companyId", "0");
        map.put("companyName", "浙江来伊份食品有限公司");
        vo.setParameter(getParameterMap(map));
        AppEntry app = new AppEntry();
        app.setSecretKey("SECRETKEY-A213574EB2FA4021A17A76");
        String sign = calcuteSign(vo, app);
        System.out.println(sign);
        System.out.println(vo.getTimeStamp());
    }

    private static Map getParameterMap(Map properties) {
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
                continue;
            }
            if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

}
