package com.rome.openapi.gateway.utils;

import com.rome.openapi.gateway.constant.OpenConatant;
import com.rome.openapi.gateway.dto.SignatureVO;
import com.rome.openapi.gateway.request.HttpServletRequestBodyWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class RequestUtil {

    /**
     * 封装验签参数
     * @param requestWrapper
     * @return
     */
    public static SignatureVO buildSignatureDTO (HttpServletRequestBodyWrapper requestWrapper){

//        String clientId = requestWrapper.getHeader(OpenConatant.HEADER_CLIENT);
        String clientId = requestWrapper.getParameter(OpenConatant.PARAMETER_CLIENT) != null ?
                requestWrapper.getParameter(OpenConatant.PARAMETER_CLIENT) : requestWrapper.getHeader(OpenConatant.HEADER_CLIENT);
        String access_token = requestWrapper.getHeader(OpenConatant.HEADER_ACCESS);
        String timestamp = requestWrapper.getHeader(OpenConatant.HEADER_TIMESTAMP);
        String sign = requestWrapper.getHeader(OpenConatant.HEADER_SIGN);
        String format = requestWrapper.getHeader(OpenConatant.HEADER_FORMAT);

        String method = requestWrapper.getMethod();
        String URI = requestWrapper.getRequestURI();

        Map<String,Object> parameters = getParameterMap(requestWrapper);

        Map<String,Object> signHeaders = requestHeaders(requestWrapper);

        String IP = IpAdrressUtil.getIpAdrress(requestWrapper);

        SignatureVO dto = new SignatureVO();
        dto.setRequestURI(URI);
        dto.setRequestMethod(method);
        dto.setClientId(clientId);
        dto.setAccessToken(access_token);
        dto.setTimeStamp(timestamp);
        dto.setSign(sign);
        dto.setFormat(format);
        dto.setRequestIP(IP);
        dto.setParameter(parameters);
        dto.setHeaders(signHeaders);

        //contentType是application/json的时候获取body并取md5值做验签
        if (MediaType.APPLICATION_JSON_VALUE.equals(requestWrapper.getContentType())){
            String requestBody = requestBodyMD5(requestWrapper);
            dto.setMd5Body(requestBody);
        }

        return dto;

    }

    /**
     * 获取body的md5
     * @param bodyWrapperWrapper
     * @return
     */
    private static String requestBodyMD5(HttpServletRequestBodyWrapper bodyWrapperWrapper){

        String body = bodyWrapperWrapper.getBody();
        if (StringUtils.isEmpty(body)) {
            return null;
        }
        return MD5Util.MD5(body);
    }

    private static Map<String,Object> requestHeaders(HttpServletRequestBodyWrapper bodyWrapperWrapper){
        Enumeration<String> headerNames = bodyWrapperWrapper.getHeaderNames();

        Map<String,Object> map = new HashMap<>();

        //所有以X-Co-开头的header都取出来参与验签
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = bodyWrapperWrapper.getHeader(key);
            //x-co-sign不能参与加签，逻辑循环
            if (StringUtils.equalsIgnoreCase(key,OpenConatant.HEADER_SIGN)){
                continue;
            }

            if (StringUtils.startsWith(key,OpenConatant.PREFIX_SIGN_HEADER)){
                map.put(key,value);
            }
        }

        //去过包含ut的hwader，也放入map参与验签
        String header_ut = bodyWrapperWrapper.getHeader(OpenConatant.UT_PARAMETER);
        if (StringUtils.isNotBlank(header_ut)){
            map.put(OpenConatant.UT_PARAMETER,header_ut);
        }

        return map;
    }

    /**
     * 获取parameter
     * @param request
     * @return
     */
    private static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
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
            if(null == valueObj)
            {
                value = "";
                continue;
            }
            if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

}
