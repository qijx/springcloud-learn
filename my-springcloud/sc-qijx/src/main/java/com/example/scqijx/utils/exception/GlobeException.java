//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.scqijx.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 参数校验全局异常处理类
 * 返回前端明确的错误信息说明
 */
@RestControllerAdvice
public class GlobeException {
	private static final Logger log = LoggerFactory.getLogger(GlobeException.class);

	public GlobeException() {
	}
//
//	@ExceptionHandler({org.springframework.web.bind.MethodArgumentNotValidException.class})
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	public S uniteExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException e) {
//		log.error("系统异常", e);
//		return Response.builderFail("999", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//	}

}
