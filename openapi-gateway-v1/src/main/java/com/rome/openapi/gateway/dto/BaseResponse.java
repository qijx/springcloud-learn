/*
 * Filename BaseResponse.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/25
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {
	private static final long serialVersionUID = 3969572379658224791L;

	private String code = "000000";
	private String message = "success";

	private T data;

}
