package com.rome.openapi.gateway.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class SignatureVO implements Serializable {

    private static final long serialVersionUID = 4477581837641348597L;

    private String requestURI;
    private String requestMethod;
    private String clientId;
    private String accessToken;
    private String timeStamp;
    private String sign;
    private String format;
    private String md5Body;
    private String requestIP;
    private String md5Header;
    private String md5Cookie;
    private boolean innerGateway = false;

    private Map<String,Object> parameter;
    private Map<String,Object> headers;
}
