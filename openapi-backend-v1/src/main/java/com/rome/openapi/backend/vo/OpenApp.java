package com.rome.openapi.backend.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OpenApp {
    private String appId;

    private String appName;

    private Boolean appType;

    private String secretKey;

    private String secretType;

    private Boolean hasWhiteList;

    private Integer signVersion;

    private String iptables;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private Boolean available;

    private Boolean deleted;
}