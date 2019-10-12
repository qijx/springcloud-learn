package com.rome.openapi.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * demo
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OpenApiReq {
    @Min(value = 100,message = "{minMessage}")
    private Integer id;

    @NotBlank(message = "不能为空")
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