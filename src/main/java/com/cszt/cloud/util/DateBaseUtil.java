package com.cszt.cloud.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 日期基础工具类
 * 
 * <pre>
 * 该基础工具类，提供获取当前毫秒，当前时间，开始时间和结束时间相差毫秒数
 * 年月日时增加或减，UTC转本地时间和本地时间转UTC时间
 * 
 * 年月日时增加或减用法
 * DateBaseUtil.dateAdd(DateBaseUtil.DATE, -2, new Date())
 * 表示日减2天。第一个参数取值可为YEAR,MONTH,DATE,HOUR，也可指定转换时间的格式
 * </pre>
 * 
 * @ClassName: DateBaseUtil
 * @Description: 日期基础工具类
 * @author: tanjin
 * @date:2018年9月25日 上午11:48:50
 */
public class DateBaseUtil {

	public static final int YEAR = 1;

	public static final int MONTH = 2;

	public static final int DATE = 5;

	public static final int HOUR = 10;

	private DateBaseUtil() {

	}

	/**
	 * 获取系统当前时间的毫秒数
	 * 
	 * @Title: getCurentDate
	 * @Description: 获取系统当前时间的毫秒数
	 * @return
	 */
	public static long getSystemTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取当前日期
	 * 
	 * @Title: getCurrentDate
	 * @Description: 获取当前日期
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取两个时间相差毫秒数
	 * 
	 * @Title: getDiffTime
	 * @Description: 获取两个时间相差毫秒数
	 * @param beginTime
	 *            开始毫秒数
	 * @param endTime
	 *            结束毫秒数
	 * @return
	 */
	public static long getDiffTime(long beginTime, long endTime) {
		return endTime - beginTime;
	}

	/**
	 * 获取两个时间相差毫秒数
	 * 
	 * @Title: getDiffTime
	 * @Description: 获取两个时间相差毫秒数
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static long getDiffTime(Date beginTime, Date endTime) {
		return getDiffTime(beginTime.getTime(), endTime.getTime());
	}

	/**
	 * 获取两个时间相差毫秒数
	 * 
	 * @Title: getDiffTime
	 * @Description: 获取两个时间相差毫秒数
	 * @param beginTime
	 *            开始时间
	 * @return
	 */
	public static long getDiffTime(Date beginTime) {
		return getDiffTime(beginTime.getTime(), getSystemTime());
	}

	/**
	 * 获取两个时间相差毫秒数
	 * 
	 * @Title: getDiffTime
	 * @Description: 获取两个时间相差毫秒数
	 * @param beginTime
	 *            开始时间毫秒数
	 * @return
	 */
	public static long getDiffTime(long beginTime) {
		return getDiffTime(beginTime, getSystemTime());
	}

	/**
	 * 日期增加
	 * 
	 * @Title: dateAdd
	 * @Description: 日期增加
	 * @param dateType
	 *            增加日期类型：年 DateBaseUtil.YEAR 月 DateBaseUtil.MONTH 日
	 *            DateBaseUtil.DATE 时 DateBaseUtil.HOUR
	 * @param number
	 *            增加数字
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String dateAdd(int dateType, int number, Date date, String format) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		int d = gc.get(dateType);
		gc.set(dateType, d + number);
		return DateConvertUtil.format(gc.getTime(), format);
	}

	/**
	 * 日期增加,默认返回日期格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @Title: dateAdd
	 * @Description: 日期增加
	 * @param dateType
	 *            增加日期类型：年 DateBaseUtil.YEAR 月 DateBaseUtil.MONTH 日
	 *            DateBaseUtil.DATE 时 DateBaseUtil.HOUR
	 * @param number
	 *            增加数字
	 * @param date
	 *            日期
	 * @return
	 */
	public static String dateAdd(int dateType, int number, Date date) {
		return dateAdd(dateType, number, date, DateTypeEnum.DATE_FORMAT_02.getFormat());
	}

	/**
	 * 日期增加
	 * 
	 * @Title: dateAdd
	 * @Description: 日期增加
	 * @param dateType
	 *            增加日期类型：年 DateBaseUtil.YEAR 月 DateBaseUtil.MONTH 日
	 *            DateBaseUtil.DATE 时 DateBaseUtil.HOUR
	 * @param number
	 *            增加数字
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String dateAdd(int dateType, int number, String date, String format) {
		try {
			return dateAdd(dateType, number, DateConvertUtil.parse(date, DateTypeEnum.DATE_FORMAT_02.getFormat()),
					format);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期增加,默认返回日期格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @Title: dateAdd
	 * @Description: 日期增加
	 * @param dateType
	 *            增加日期类型：年 DateBaseUtil.YEAR 月 DateBaseUtil.MONTH 日
	 *            DateBaseUtil.DATE 时 DateBaseUtil.HOUR
	 * @param number
	 *            增加数字
	 * @param date
	 *            日期
	 * @return
	 */
	public static String dateAdd(int dateType, int number, String date) {
		return dateAdd(dateType, number, date, DateTypeEnum.DATE_FORMAT_02.getFormat());
	}

	/**
	 * UTC时间转本地时间格式
	 * 
	 * @Title: utcToLocal
	 * @Description: UTC时间转本地时间格式
	 * @param utcTime
	 *            utc时间
	 * @param localTimePattern
	 *            需要转换的时间格式
	 * @return
	 */
	public static String utcToLocal(String utcTime, String localTimePattern) {
		String utcTimePattern = DateTypeEnum.DATE_FORMAT_05.getFormat();
		String subTime = utcTime.substring(10);// UTC时间格式以 yyyy-MM-dd
												// 开头,将utc时间的前10位截取掉,之后是含有多时区时间格式信息的数据
		// 处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
		if (subTime.indexOf("+") != -1) {
			subTime = changeUtcSuffix(subTime, "+");
		}
		if (subTime.indexOf("-") != -1) {
			subTime = changeUtcSuffix(subTime, "-");
		}
		utcTime = utcTime.substring(0, 10) + subTime;

		// 依据传入函数的utc时间,得到对应的utc时间格式
		// 步骤一:处理 T
		if (utcTime.indexOf("T") != -1) {
			utcTimePattern = utcTimePattern + "'T'";
		}
		// 步骤二:处理毫秒SSS
		if (utcTime.indexOf(".") != -1) {
			utcTimePattern = utcTimePattern + "HH:mm:ss.SSS";
		} else {
			utcTimePattern = utcTimePattern + "HH:mm:ss";
		}
		// 步骤三:处理时区问题
		if (subTime.indexOf("+") != -1 || subTime.indexOf("-") != -1) {
			utcTimePattern = utcTimePattern + "XXX";
		} else if (subTime.indexOf("Z") != -1) {
			utcTimePattern = utcTimePattern + "'Z'";
		}
		if (DateTypeEnum.DATE_FORMAT_02.getFormat().equals(utcTimePattern)
				|| DateTypeEnum.DATE_FORMAT_04.getFormat().equals(utcTimePattern)) {
			return utcTime;
		}

		Date gpsUtcDate = null;
		try {
			gpsUtcDate = DateConvertUtil.parse(utcTime, utcTimePattern, "UTC");
		} catch (Exception e) {
			return utcTime;
		}
		return DateConvertUtil.format(gpsUtcDate, localTimePattern, "Asia/Shanghai");
	}

	private static String changeUtcSuffix(String subTime, String sign) {
		String timeSuffix = null;
		String[] splitTimeArrayOne = subTime.split("\\" + sign);
		String[] splitTimeArrayTwo = splitTimeArrayOne[1].split(":");
		if (splitTimeArrayTwo[0].length() < 2) {
			timeSuffix = "+" + "0" + splitTimeArrayTwo[0] + ":" + splitTimeArrayTwo[1];
			subTime = splitTimeArrayOne[0] + timeSuffix;
			return subTime;
		}
		return subTime;
	}

	/**
	 * 本地时间转换为UTC时间
	 * @Title: localToUTC   
	 * @Description: 本地时间转换为UTC时间  
	 * @param localTime
	 * @return
	 */
	public static Date localToUTC(String localTime) {
		Date localDate = null;
		try {
			localDate = DateConvertUtil.parse(localTime, DateTypeEnum.DATE_FORMAT_02);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long localTimeInMillis = localDate.getTime();
		/** long时间转换成Calendar */
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(localTimeInMillis);
		calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		/** 取得时间偏移量 */
		int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
		/** 取得夏令时差 */
		int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
		/** 从本地时间里扣除这些差量，即可以取得UTC时间 */
		calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		/** 取得的时间就是UTC标准时间 */
		Date utcDate = new Date(calendar.getTimeInMillis());
		return utcDate;
	}
}
