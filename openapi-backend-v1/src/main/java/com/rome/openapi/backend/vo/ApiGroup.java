/*
 * Filename ApiGroup.java
 * Company ??????????????????????????????????????????
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/8/7
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApiGroup implements Serializable {
	private Integer id;
	private String service;
	private String group;
}
