package com.rome.openapi.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * demo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiResp extends BaseResponse{
    private Integer id;

    private String name;

    private String path;

    private Integer groupId;

    private String method;

    private String version;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private Boolean available;

    private Boolean deleted;
}