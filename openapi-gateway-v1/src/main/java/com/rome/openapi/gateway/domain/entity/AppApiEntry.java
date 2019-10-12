package com.rome.openapi.gateway.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AppApiEntry implements Serializable {

    private static final long serialVersionUID = 8045909980265979482L;

    private Integer id;

    private String appId;

    private Integer apiId;

    private String selectField;
}
