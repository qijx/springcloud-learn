package com.rome.openapi.backend.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class AppApiEntry {
    private Integer id;

    private String appId;

    private Integer apiId;

    private String selectField;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private Boolean available;

    private Boolean deleted;
}