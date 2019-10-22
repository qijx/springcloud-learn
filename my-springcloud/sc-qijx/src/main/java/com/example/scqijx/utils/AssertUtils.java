package com.example.scqijx.utils;


/**
 * 断言工具类
 *
 */
public class AssertUtils {

	/**
	 * 判断一个Integer是否是大于0的有效数
	 * @param number 数字
	 * @return true-有效，false-无效
	 */
	public static boolean isValidNumber(Integer number){
		return number != null && number > 0;
	}

}
