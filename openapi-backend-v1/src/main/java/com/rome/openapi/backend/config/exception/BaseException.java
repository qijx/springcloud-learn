/*
 * Filename BaseException.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.config.exception;

/**
 * 自定义异常类
 *  @author kongweixiang
 *  @since 1.0.0_2018/7/24
 */
public class BaseException extends RuntimeException {
    private String code;
    private String message;

    public BaseException() {
        super();
    }

    public BaseException(String code,String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
