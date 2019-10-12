/*
 * Filename IdUtil.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.util.id;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
public class IdUtil {
	public static String getId24() {
		return ObjectId.get() + "";
	}

	public static Long getUUIDLong() {
		return KeyGenerator.getInstance().getLongPK();
	}

	public static String getUUID25() {
		return KeyGenerator.getInstance().getStringPK25();
	}

	public static String getUUID27() {
		return KeyGenerator.getInstance().getStringPK27();
	}

	public static String getUUID32() {
		return KeyGenerator.getInstance().getStringPK32();
	}

	public static String getUUID(int count) {
		return KeyGenerator.getInstance().getStringPK(count);
	}

	public static String getUUID(String p, int count) {
		return KeyGenerator.getInstance().getStringPK(p, count);
	}

	public static String getUUID36() {
		return KeyGenerator.getInstance().getUUID();
	}

	public static String getIdLeft(String initParameterLeft) {
		return ObjectId.get() + "";
	}

	public static String getId32UpperCase() {
		String id = KeyGenerator.getInstance().getUUID();
		return id.toUpperCase();
	}

	public static String getIdUpperCaseRight(int count) {
		String id = KeyGenerator.getInstance().getUUIDString(count);
		return id.toUpperCase();
	}

	public static String getIdUpperCaseRight(String righParamt, int count) {
		String id = KeyGenerator.getInstance().getUUIDRighParamt(righParamt, count);
		return id.toUpperCase();
	}

	public static String getIdUpperCaseLeft(String leftParamt, int count) {
		String id = KeyGenerator.getInstance().getUUIDLeft(leftParamt, count);
		return id.toUpperCase();
	}

	public static String getIdRight(String initParameterRight) {
		return ObjectId.get() + "";
	}
}

