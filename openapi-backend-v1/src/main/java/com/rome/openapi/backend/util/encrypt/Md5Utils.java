/*
 * Filename Md5Utils.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.util.encrypt;

import com.rome.openapi.backend.util.id.IdUtil;

import java.security.MessageDigest;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
public class Md5Utils {
	private static final String[] hexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

	public Md5Utils() {
	}

	public static String md5Key32() {
		return IdUtil.getId32UpperCase();
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		byte[] arr$ = b;
		int len$ = b.length;

		for(int i$ = 0; i$ < len$; ++i$) {
			byte aB = arr$[i$];
			resultSb.append(byteToHexString(aB));
		}

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (b < 0) {
			n = 256 + b;
		}

		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String encoding) {
		String resultString = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(origin.getBytes(encoding)));
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		return resultString;
	}
}
