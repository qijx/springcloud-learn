/*
 * Filename AppEntry.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/30
 */
@Getter
@Setter
@NoArgsConstructor
public class AppEntry implements Serializable{
	private String appId;

	private String appName;

	private Boolean appType;

	private String secretKey;

	private String secretType;

	private Boolean hasWhiteList;

	private Integer signVersion;

	private Boolean available;

	private String IP;
}
