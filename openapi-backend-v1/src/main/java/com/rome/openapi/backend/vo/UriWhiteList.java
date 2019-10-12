package com.rome.openapi.backend.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UriWhiteList implements Serializable{

    private static final long serialVersionUID = -8446675543107670724L;

    private Integer id;

    private String uri;

    private Date createTime;

    private String createUser;

    private Boolean available;

    private Boolean deleted;

}