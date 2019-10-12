/*
 * Filename openApiGroup.java
 * Company ??????????????????????????????????????????
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/8/9
 */
@Getter
@Setter
@NoArgsConstructor
public class OpenApiGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String service;
	private String group;
	private Date createTime;
	private String createUser;
	private Integer available;
	private Integer deleted;
}