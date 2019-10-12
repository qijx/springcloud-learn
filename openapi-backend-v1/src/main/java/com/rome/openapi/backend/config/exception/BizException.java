/*
 * Filename BizException.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.config.exception;

/**
 *  自定义业务异常类
 * @author kongweixiang
 * @since 1.0.0_2018/7/24
 */
public class BizException extends BaseException {

	public BizException(String code, String message) {
		super(code, message);
	}
}
