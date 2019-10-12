/*
 * Filename DESedeUtil.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
public class DESedeUtil {

	private static final String Algorithm = "DESede";
	public static String DESEDEKEY = "123456789032564128901434567860123654709012345678";

	public DESedeUtil() {
	}

	public static String getKeyType() {
		return "DESede";
	}

	public static void putKey(String key) {
		DESEDEKEY = key;
	}

	private static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
			Cipher c1 = Cipher.getInstance("DESede");
			c1.init(1, deskey);
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException var4) {
			var4.printStackTrace();
		} catch (NoSuchPaddingException var5) {
			var5.printStackTrace();
		} catch (Exception var6) {
			var6.printStackTrace();
		}

		return null;
	}

	private static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
			Cipher c1 = Cipher.getInstance("DESede");
			c1.init(2, deskey);
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException var4) {
			var4.printStackTrace();
		} catch (NoSuchPaddingException var5) {
			var5.printStackTrace();
		} catch (Exception var6) {
			var6.printStackTrace();
		}

		return null;
	}

	private static String byte2hex(byte[] b) {
		String stmp = "";
		StringBuffer sb = new StringBuffer();

		for(int n = 0; n < b.length; ++n) {
			stmp = Integer.toHexString(b[n] & 255);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}
		}

		return sb.toString().toUpperCase();
	}

	private static byte[] hex2byte(String s) throws Exception {
		if (s.length() % 2 != 0) {
			throw new Exception("密钥格式不正确");
		} else {
			byte[] ret = new byte[s.length() / 2];
			int i = 0;

			while(i < s.length()) {
				char c = s.charAt(i);
				++i;
				char c1 = s.charAt(i);
				if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f') {
					if (c1 >= '0' && c1 <= '9' || c1 >= 'A' && c1 <= 'F' || c1 >= 'a' && c1 <= 'f') {
						int x = Integer.decode("0x" + c + c1);
						if (x > 127) {
							ret[i / 2] = (byte)(x | -256);
						} else {
							ret[i / 2] = (byte)x;
						}

						++i;
						continue;
					}

					throw new Exception("密钥格式不正确");
				}

				throw new Exception("密钥格式不正确");
			}

			return ret;
		}
	}

	public static String encrypt(String src) throws Exception {
		try {
			byte[] encoded = encryptMode(hex2byte(DESEDEKEY), src.getBytes());
			return byte2hex(encoded);
		} catch (Exception var2) {
			throw new Exception(var2.getMessage());
		}
	}

	public static String encrypt(String src, String desedeKey) throws Exception {
		try {
			byte[] encoded = encryptMode(hex2byte(desedeKey), src.getBytes());
			return byte2hex(encoded);
		} catch (Exception var3) {
			throw new Exception(var3.getMessage());
		}
	}

	public static String decrypt(String src) throws Exception {
		try {
			byte[] srcByte = hex2byte(src);
			byte[] decoded = decryptMode(hex2byte(DESEDEKEY), srcByte);
			return new String(decoded);
		} catch (Exception var3) {
			throw new Exception(var3.getMessage());
		}
	}

	public static String decrypt(String src, String desedeKey) throws Exception {
		try {
			byte[] srcByte = hex2byte(src);
			byte[] decoded = decryptMode(hex2byte(desedeKey), srcByte);
			return new String(decoded);
		} catch (Exception var4) {
			throw new Exception(var4.getMessage());
		}
	}
}
