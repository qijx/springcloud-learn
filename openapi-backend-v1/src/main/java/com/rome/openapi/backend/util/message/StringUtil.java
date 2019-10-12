/*
 * Filename StringUtil.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.util.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
public class StringUtil {
	public static String LINE = new String(new byte[]{13, 10});
	private static final int PAD_LIMIT = 8192;
	public static final int FRONT = 0;
	public static final int BACK = 1;

	public StringUtil() {
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean contains(String a, String b) {
		return a.contains(b);
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str != null && (strLen = str.length()) != 0) {
			for(int i = 0; i < strLen; ++i) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
		if (repeat < 0) {
			throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
		} else {
			char[] buf = new char[repeat];

			for(int i = 0; i < buf.length; ++i) {
				buf[i] = padChar;
			}

			return new String(buf);
		}
	}

	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		} else {
			int pads = size - str.length();
			if (pads <= 0) {
				return str;
			} else {
				return pads > 8192 ? rightPad(str, size, String.valueOf(padChar)) : str.concat(padding(pads, padChar));
			}
		}
	}

	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		} else {
			if (isEmpty(padStr)) {
				padStr = " ";
			}

			int padLen = padStr.length();
			int strLen = str.length();
			int pads = size - strLen;
			if (pads <= 0) {
				return str;
			} else if (padLen == 1 && pads <= 8192) {
				return rightPad(str, size, padStr.charAt(0));
			} else if (pads == padLen) {
				return str.concat(padStr);
			} else if (pads < padLen) {
				return str.concat(padStr.substring(0, pads));
			} else {
				char[] padding = new char[pads];
				char[] padChars = padStr.toCharArray();

				for(int i = 0; i < pads; ++i) {
					padding[i] = padChars[i % padLen];
				}

				return str.concat(new String(padding));
			}
		}
	}

	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		} else {
			int pads = size - str.length();
			if (pads <= 0) {
				return str;
			} else {
				return pads > 8192 ? leftPad(str, size, String.valueOf(padChar)) : padding(pads, padChar).concat(str);
			}
		}
	}

	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		} else {
			if (isEmpty(padStr)) {
				padStr = " ";
			}

			int padLen = padStr.length();
			int strLen = str.length();
			int pads = size - strLen;
			if (pads <= 0) {
				return str;
			} else if (padLen == 1 && pads <= 8192) {
				return leftPad(str, size, padStr.charAt(0));
			} else if (pads == padLen) {
				return padStr.concat(str);
			} else if (pads < padLen) {
				return padStr.substring(0, pads).concat(str);
			} else {
				char[] padding = new char[pads];
				char[] padChars = padStr.toCharArray();

				for(int i = 0; i < pads; ++i) {
					padding[i] = padChars[i % padLen];
				}

				return (new String(padding)).concat(str);
			}
		}
	}

	public static String fill(String p_scr, char p_fill, int p_length) {
		return fill(p_scr, p_fill, p_length, 0);
	}

	public static String fill(String p_scr, char p_fill, int p_length, int direction) {
		if (p_scr.length() == p_length) {
			return p_scr;
		} else {
			char[] fill = new char[p_length - p_scr.length()];
			Arrays.fill(fill, p_fill);
			switch(direction) {
				case 0:
					return String.valueOf(fill).concat(p_scr);
				case 1:
					return p_scr.concat(String.valueOf(fill));
				default:
					return p_scr;
			}
		}
	}

	public static String fillString(String p_scr, char p_fill, int p_length, int direction) {
		if (p_scr.getBytes().length == p_length) {
			return p_scr;
		} else {
			char[] fill = new char[p_length - p_scr.getBytes().length];
			Arrays.fill(fill, p_fill);
			switch(direction) {
				case 0:
					return String.valueOf(fill).concat(p_scr);
				case 1:
					return p_scr.concat(String.valueOf(fill));
				default:
					return p_scr;
			}
		}
	}

	public static String array2String(Object[] param, String delim) {
		if (param == null) {
			return null;
		} else {
			delim = delim != null ? delim : ",";
			StringBuffer result = new StringBuffer();

			for(int i = 0; i < param.length; ++i) {
				result.append(param[i]);
				if (i < param.length - 1) {
					result.append(delim);
				}
			}

			return result.toString();
		}
	}

	public static String array2String(Object[] param, String prefix, String suffix, String delim) {
		if (param == null) {
			return null;
		} else {
			delim = delim != null ? delim : ",";
			prefix = prefix != null ? prefix : "";
			suffix = suffix != null ? suffix : "";
			StringBuffer result = new StringBuffer();

			for(int i = 0; i < param.length; ++i) {
				result.append(prefix).append(param[i]).append(suffix);
				if (i < param.length - 1) {
					result.append(delim);
				}
			}

			return result.toString();
		}
	}

	public static String[] string2Array(String string, String delim) {
		if (string == null) {
			return null;
		} else {
			delim = delim != null ? delim : "\\|";
			return string.split(delim);
		}
	}

	public static List<String> string2List(String p_Param, String p_Delim) {
		if (p_Param == null) {
			return null;
		} else {
			StringTokenizer st;
			if (p_Delim != null) {
				st = new StringTokenizer(p_Param, p_Delim);
			} else {
				st = new StringTokenizer(p_Param);
			}

			ArrayList result = new ArrayList();

			while(st.hasMoreTokens()) {
				result.add(st.nextToken());
			}

			return result;
		}
	}

	public static String encoding(String str) throws Exception {
		return new String(str.getBytes("GB23"), "GB23");
	}

	public static String trimHeadAndEnd(String str) {
		if (str == null) {
			return null;
		} else {
			String temp = str.trim();
			if (temp.length() <= 0) {
				return null;
			} else {
				while(temp.indexOf(" ") == 0) {
					temp = temp.substring(1, temp.length());
				}

				return temp;
			}
		}
	}

	public static String replaceAll(String rep, String[] redex, String[] replaceement) {
		if (redex.length == replaceement.length) {
			for(int i = 0; i < redex.length; ++i) {
				rep = rep.replaceAll("\\" + redex[i], replaceement[i]);
			}
		}

		return rep;
	}

	public static String stirngReplaceIndex(int index, String res, String str, int end) {
		String value = res;
		if (res.length() > 10) {
			for(int i = index; i < res.length() - end; ++i) {
				value = replaceIndex(i, value, str);
			}
		}

		return value;
	}

	public static String replaceIndex(int index, String res, String str) {
		return res.substring(0, index) + str + res.substring(index + 1);
	}
}
