/*
 * Filename IDService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.util.constant;

import com.rome.openapi.backend.util.id.IdUtil;

/**
 * 提供id
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
public class IDService {

	/**
	 * 生成数据库ID
	 * @return
	 */
	public static String getID() {
		return IdUtil.getId24();
	}

	/**
	 * 生成AppKey
	 */
	public static String getAppId() {
		return IdUtil.getId32UpperCase();
	}
}
