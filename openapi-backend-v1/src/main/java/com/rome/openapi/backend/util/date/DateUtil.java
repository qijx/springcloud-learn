/*
 * Filename DateUtil.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.util.date;

import com.rome.openapi.backend.util.message.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/7/27
 */
public class DateUtil {
	public static final int ONE_SECOND = 1;
	public static final int ONE_MINUTE = 60;
	public static final int ONE_HOUR = 3600;
	public static final int ONE_DAY = 86400;
	public static final int ONE_MONTH = 2592000;
	public static final int MONTH_DAY = 30;
	public static final int ONE_YEAR = 31536000;
	public static final int YEAR_DAY = 365;
	public static final String DD = "dd";
	public static final String MM_DD = "MM-dd";
	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMM = "yyyyMM";
	public static final String MMDD = "MMdd";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYY = "yyyy";
	public static final String YYYYMMDDHH = "yyyyMMddHH";
	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYY_CN = "yyyy年";
	public static final String YYYY_MM_CN = "yyyy年MM月";
	public static final String MM_DD_CN = "MM月dd日";
	public static final String YYYY_MM_DD_CN = "yyyy年MM月dd日";
	public static final String YYYY_MM_DD_HH_CN = "yyyy年MM月dd日 HH时";
	public static final String YYYY_MM_DD_HH_MM_CN = "yyyy年MM月dd日 HH时mm分";
	public static final String YYYY_MM_DD_HH_MM_SS_CN = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String HH = "HH";
	public static final String MM = "mm";
	public static final String SS = "ss";
	public static final String HH_MM = "HH:mm";
	public static final String MM_SS = "mm:ss";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String HHMM = "HHmm";
	public static final String MMSS = "mmss";
	public static final String HHMMSS = "HHmmss";
	public static final String HH_MM_CN = "HH时mm分";
	public static final String MM_SS_CN = "mm分ss秒";
	public static final String HH_MM_SS_CN = "HH时mm分ss秒";

	public DateUtil() {
	}

	public static boolean leapYear(int year) {
		Boolean isLeap = false;
		if (year % 100 == 0 && year % 400 == 0 || year % 100 != 0 && year % 4 == 0) {
			isLeap = true;
		}

		return isLeap;
	}

	public static final String getDD() {
		SimpleDateFormat sdf = getSimpleDateFormat("dd");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getMM_DD() {
		SimpleDateFormat sdf = getSimpleDateFormat("MM-dd");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_MM() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_MM_DD() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYYMM() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyyMM");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getMMDD() {
		SimpleDateFormat sdf = getSimpleDateFormat("MMdd");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYYMMDD() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMdd");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYYMMDDHH() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMddHH");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYYMMDDHHMM() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMddHHmm");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYYMMDDHHMMSS() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_CN() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy年");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_MM_CN() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy年MM月");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getMM_DD_CN() {
		SimpleDateFormat sdf = getSimpleDateFormat("MM月dd日");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_MM_DD_CN() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_MM_DD_HH_CN() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy年MM月dd日 HH时");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_MM_DD_HH_MM_CN() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy年MM月dd日 HH时mm分");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_MM_DD_HH_MM_SS_CN() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_MM_DD_HH() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_MM_DD_HH_MM() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getYYYY_MM_DD_HH_MM_SS() {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getHH() {
		SimpleDateFormat sdf = getSimpleDateFormat("HH");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getMM() {
		SimpleDateFormat sdf = getSimpleDateFormat("mm");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getSS() {
		SimpleDateFormat sdf = getSimpleDateFormat("ss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getHH_MM() {
		SimpleDateFormat sdf = getSimpleDateFormat("HH:mm");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getMM_SS() {
		SimpleDateFormat sdf = getSimpleDateFormat("mm:ss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getHH_MM_SS() {
		SimpleDateFormat sdf = getSimpleDateFormat("HH:mm:ss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getHHMM() {
		SimpleDateFormat sdf = getSimpleDateFormat("HHmm");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getMMSS() {
		SimpleDateFormat sdf = getSimpleDateFormat("mmss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getHHMMSS() {
		SimpleDateFormat sdf = getSimpleDateFormat("HHmmss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getHH_MM_CN() {
		SimpleDateFormat sdf = getSimpleDateFormat("HH时mm分");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getMM_SS_CN() {
		SimpleDateFormat sdf = getSimpleDateFormat("mm分ss秒");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getHH_MM_SS_CN() {
		SimpleDateFormat sdf = getSimpleDateFormat("HH时mm分ss秒");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String getDate(String dataFormat) {
		SimpleDateFormat sdf = getSimpleDateFormat(dataFormat);
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static String convertDataFormat(String str) {
		if (str == null) {
			return "";
		} else {
			String result = str;
			String regStr1 = "\\d{4}[-]\\d{2}[-]\\d{2}";
			String regStr2 = "-";
			if (Pattern.matches(regStr1, str)) {
				Pattern pattern = Pattern.compile(regStr2);
				Matcher matcher = pattern.matcher(str);
				result = matcher.replaceAll("");
			}

			return result;
		}
	}

	public static List<Integer> getMONTN(String start, String end) {
		List<Integer> list = new ArrayList();
		int bing = Integer.valueOf(day_MONTH(start));
		int ends = Integer.valueOf(day_MONTH(end));

		for(int i = bing; i <= ends; ++i) {
			list.add(i);
		}

		return list;
	}

	private static String day_MONTH(String day) {
		return convertDataFormat(day).substring(4, 6);
	}

	public static int day_Week(String today) {
		try {
			Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(today);
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(2);
			calendar.setTime(date);
			return calendar.get(3);
		} catch (Exception var3) {
			var3.printStackTrace();
			return -1;
		}
	}

	public static List<Date> getAllDates(Date start, Date end) {
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(start);
		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(end);
		return dayAll(calendar1, calendar2, start);
	}

	private static List<Date> dayAll(Calendar calendar1, Calendar calendar2, Date start) {
		List<Date> list = new ArrayList();
		list.add(start);

		while(calendar1.compareTo(calendar2) < 0) {
			calendar1.add(5, 1);
			list.add(calendar1.getTime());
		}

		return list;
	}

	public static List<Date> getAllDatesStr(String start, String end) {
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(parseDate(start));
		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(parseDate(end));
		return dayAll(calendar1, calendar2, parseDate(start));
	}

	public static String getLastMonday(String str) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTime(parseDate(str));
		ca.set(7, 2);
		ca.add(3, -1);
		ca.get(7);
		return convertDataFormat(sf.format(ca.getTime()));
	}

	public static String getLastSunday(String str) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTime(parseDate(str));
		ca.set(7, 1);
		ca.add(3, 0);
		ca.get(7);
		return convertDataFormat(sf.format(ca.getTime()));
	}

	public static Date getLastYear(Date now) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(now);
		calendar.add(1, -1);
		calendar.set(2, 0);
		calendar.set(5, 1);
		return calendar.getTime();
	}

	public static boolean isSameDay(Date d1, Date d2) {
		boolean result = false;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		if (c1.get(1) == c2.get(1) && c1.get(2) == c2.get(2) && c1.get(5) == c2.get(5)) {
			result = true;
		}

		return result;
	}

	public static String getQuarter(String dataStr) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(parseDate(dataStr));
		return String.valueOf(ca.get(2) / 3 + 1);
	}

	public static Date getNextDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(5);
		calendar.set(5, day + 1);
		return calendar.getTime();
	}

	public static int day_Week() {
		Calendar calendar = Calendar.getInstance();
		int weekOfYear = calendar.get(3);
		return weekOfYear;
	}

	public static List<Date> getAllDates(String YEAR) {
		return getAllDates(parseDate(getCurrentYearFirst(YEAR)), parseDate(getCurrentYearEnd(YEAR)));
	}

	public static String getCurrentYearEnd(String YEAR) {
		return YEAR + "-12-31";
	}

	public static String getCurrentYearFirst(String YEAR) {
		return YEAR + "-01-01";
	}

	public static String nextDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(5, 1);
		return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
	}

	public static List<String> getAllDatesString(String start, String end) {
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(parseDate(start));
		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(parseDate(end));
		return dayAllStr(calendar1, calendar2, start);
	}

	private static List<String> dayAllStr(Calendar calendar1, Calendar calendar2, String start) {
		List<String> list = new ArrayList();
		list.add((new SimpleDateFormat("yyyyMMdd")).format(parseDate(start)));

		while(calendar1.compareTo(calendar2) < 0) {
			calendar1.add(5, 1);
			list.add((new SimpleDateFormat("yyyyMMdd")).format(calendar1.getTime()));
		}

		return list;
	}

	public static String formatDuring(long times) {
		long days = times / 86400000L;
		long hours = times % 86400000L / 3600000L;
		long minutes = times % 3600000L / 60000L;
		long seconds = times % 60000L / 1000L;
		return days + " 天 " + hours + " 小时 " + minutes + " 分钟 " + seconds + " 秒 ";
	}

	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";

		try {
			Date date1 = format.parse(sj1);
			long Time = date1.getTime() / 1000L + (long)(Integer.parseInt(jj) * 60);
			date1.setTime(Time * 1000L);
			mydate1 = format.format(date1);
		} catch (Exception var7) {
			var7.printStackTrace();
		}

		return mydate1;
	}

	public static long getIntervalDate(String perDate, String backDate) throws Exception {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMdd");
		long perTime = sdf.parse(perDate).getTime();
		long backTime = sdf.parse(backDate.substring(0, 8)).getTime();
		return (backTime - perTime) / 1000L / 60L / 60L / 24L;
	}

	public static long getIntervalTime(String startTime, String endTime) throws Exception {
		SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMddHHmmss");
		long perTime = sdf.parse(startTime).getTime();
		long backTime = sdf.parse(endTime).getTime();
		return (backTime - perTime) / 1000L;
	}

	public static String getPreTimeSecond(String sj1, int second) throws Exception {
		if (sj1.length() < 19) {
			sj1 = getYYMMDDHHmmssString(sj1);
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";

		try {
			Date date1 = format.parse(sj1);
			long Time = date1.getTime() / 1000L + (long)second;
			date1.setTime(Time * 1000L);
			format = new SimpleDateFormat("yyyyMMddHHmmss");
			mydate1 = format.format(date1);
		} catch (Exception var7) {
			var7.printStackTrace();
		}

		return mydate1;
	}

	private static String getYYMMDDHHmmssString(String value) {
		StringBuffer bean = new StringBuffer();
		String returValue = null;
		if (value != null) {
			String b = value.substring(0, 4);
			bean.append(b);
			if (value.indexOf("-") != -1 && value.indexOf(":") != -1) {
				returValue = value.toString();
			} else {
				bean.append("-" + value.substring(4, 6) + "-" + value.substring(6, 8) + " " + value.substring(8, 10) + ":" + value.substring(10, 12) + ":" + value.substring(12, 14));
				returValue = bean.toString();
			}
		}

		return returValue;
	}

	private static Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy-MM-dd");
	}

	public static Date parseDate(String dateStr, String pattern) {
		if (dateStr != null && dateStr.length() != 0 && pattern != null && pattern.length() != 0) {
			DateFormat fmt = new SimpleDateFormat(pattern);
			Date result = null;

			try {
				result = fmt.parse(dateStr);
			} catch (ParseException var5) {
				var5.printStackTrace();
			}

			return result;
		} else {
			return null;
		}
	}

	private static SimpleDateFormat getSimpleDateFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	public static boolean bigTime(String s1, String s2) {
		boolean sign = false;
		if (StringUtil.isEmpty(s1)) {
			return false;
		} else if (StringUtil.isEmpty(s2)) {
			return false;
		} else {
			if (s1.length() < 19) {
				s1 = getYYMMDDHHmmssString(s1);
			}

			if (s2.length() < 19) {
				s2 = getYYMMDDHHmmssString(s2);
			}

			int result = result(s1, s2);
			if (result > 0) {
				sign = true;
			} else {
				sign = false;
			}

			return sign;
		}
	}

	public static int result(String s1, String s2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		try {
			c1.setTime(df.parse(s1));
			c2.setTime(df.parse(s2));
		} catch (ParseException var6) {
			System.err.println("格式不正确");
		}

		int result = c1.compareTo(c2);
		return result;
	}
}
