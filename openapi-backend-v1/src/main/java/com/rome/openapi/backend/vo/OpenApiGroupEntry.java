package com.rome.openapi.backend.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenApiGroupEntry extends OpenApi{
    private Integer serviceId;
    private String service;
    private String group;
}