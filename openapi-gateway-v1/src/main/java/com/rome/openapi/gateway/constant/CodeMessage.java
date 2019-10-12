/*
 * Filename CodeMessage.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.constant;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/30
 */
public enum CodeMessage {
	VALIDMESSAGE("101101", "字段不合法"),
	NOFINDAPI("200001", "未匹配到api"),
	APIFORBIDDEN("200002", "非法访问api"),
	IPWHITENOTEXIST("200003", "当前访问ip不在白名单内"),
	APPAPISNOTEXIST("200004", ""),
	SYSERR("200999", "系统异常,请联系管理员"),
	UNDEFINED_CODE("200998", "系统异常,请联系管理员"),
	SIGN_FAIL("200005", "签名失败"),
	TIME_FAIL("200006", "时间戳验证失败"),
	ROUTE_FAIL("400998", "服务系统异常,请联系管理员"),

	URIWHITE_FAIL("200007", "URL白名单验证不存在"),;
	private String code;
	private String message;

	CodeMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static CodeMessage parse(String code) {
		CodeMessage[] item = values();
		for (int i = 0; i < item.length; i++) {
			if (item[i].code == code) {
				return item[i];
			}
		}
		return UNDEFINED_CODE;
	}
}
