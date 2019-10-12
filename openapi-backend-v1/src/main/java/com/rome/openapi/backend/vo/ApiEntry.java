/*
 * Filename ApiEntry.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * api列表
 * @author kongweixiang
 * @since 1.0.0_2018/7/30
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ApiEntry implements Serializable {

	private Integer id;

	private String name;

	private String path;

	private String method;

	private String version;

	private Boolean available;

	private List<String> appKey;
}
