/*
 * Filename JSONStyleException.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.example.scgateway.exception;


import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回json风格的response
 */
public class JSONStyleException extends RuntimeException{
	private String code = "400999";
	private String message = "系统异常,请联系服务方";

	public JSONStyleException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public JSONStyleException() {
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

	public String toString() {
		Map map = new HashMap();
		map.put("code", getCode());
		map.put("message", message);
		return new Gson().toJson(map);
	}
}
