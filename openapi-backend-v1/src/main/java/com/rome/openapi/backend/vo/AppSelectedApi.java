package com.rome.openapi.backend.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppSelectedApi implements Serializable {
    private static final long serialVersionUID = -8211774186481641950L;

    private Integer id;

    private String appId;

    private String apiId;

    private String apiName;

    private String selectedFields;
}
