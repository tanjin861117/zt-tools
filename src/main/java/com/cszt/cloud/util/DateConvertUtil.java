package com.cszt.cloud.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期转换工具类
 * 
 * <pre>
 * 将日期转换string
 * DateConvertUtil.format(new Date(), DateTypeEnum.DATE_FORMAT_02)
 * 或
 * DateConvertUtil.format(new Date(), DateTypeEnum.DATE_FORMAT_02.getFormat())
 * 
 * 将string转换日期
 * DateConvertUtil.parse("2018-09-25 11:47:14", DateTypeEnum.DATE_FORMAT_02)
 * 或
 * DateConvertUtil.parse("2018-09-25 11:47:14", DateTypeEnum.DATE_FORMAT_02.getFormat())
 * </pre>
 * 
 * @ClassName: DateConvertUtil
 * @Description: 日期转换工具类
 * @author: tanjin
 * @date:2018年9月25日 上午11:08:41
 */
public class DateConvertUtil {

	private DateConvertUtil() {

	}

	/**
	 * 获取日志转换类
	 * 
	 * @Title: getDateFormat
	 * @Description: 获取日志转换类
	 * @param format
	 * @return
	 */
	private static DateFormat getDateFormat(String format) {
		return new SimpleDateFormat(format);
	}

	/**
	 * 将string类型转换为日期
	 * 
	 * @Title: parse
	 * @Description: 将string类型转换为日期
	 * @param source
	 *            string日期
	 * @param type
	 *            日期枚举类型
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String source, DateTypeEnum type) throws ParseException {
		return parse(source, type.getFormat());
	}

	/**
	 * 将string类型转换为日期
	 * 
	 * @Title: parse
	 * @Description: 将string类型转换为日期
	 * @param source
	 *            string日期
	 * @param type
	 *            日期枚举类型
	 * @param zone
	 *            时区
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String source, DateTypeEnum type, String zone) throws ParseException {
		return parse(source, type.getFormat(), zone);
	}

	/**
	 * 将string类型转换为日期
	 * 
	 * @Title: parse
	 * @Description: 将string类型转换为日期
	 * @param source
	 *            string日期
	 * @param patten
	 *            日期枚举类型格式
	 * @param zone
	 *            时区
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String source, String patten, String zone) throws ParseException {
		DateFormat formate = getDateFormat(patten);
		formate.setTimeZone(TimeZone.getTimeZone(zone));
		return formate.parse(source);
	}

	/**
	 * 将string类型转换为日期
	 * 
	 * @Title: parse
	 * @Description: 将string类型转换为日期
	 * @param source
	 *            string日期
	 * @param patten
	 *            日期枚举类型格式
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String source, String patten) throws ParseException {
		DateFormat formate = getDateFormat(patten);
		return formate.parse(source);
	}

	/**
	 * 将日期转换为string
	 * 
	 * @Title: format
	 * @Description: 将日期转换为string
	 * @param date
	 *            日期
	 * @param patten
	 *            日期枚举类型格式
	 * @return
	 */
	public static String format(Date date, String patten) {
		DateFormat formate = getDateFormat(patten);
		return formate.format(date);
	}
	
	/**
	 * 将日期转换为string
	 * 
	 * @Title: format
	 * @Description: 将日期转换为string
	 * @param date
	 *            日期
	 * @param patten
	 *            日期枚举类型格式
	 * @param zone
	 *            时区
	 * @return
	 */
	public static String format(Date date, String patten, String zone) {
		DateFormat formate = getDateFormat(patten);
		formate.setTimeZone(TimeZone.getTimeZone(zone));
		return formate.format(date);
	}
	
	/**
	 * 将日期转换为string
	 * 
	 * @Title: format
	 * @Description: 将日期转换为string
	 * @param date
	 *            日期
	 * @param type
	 *            日期枚举类型
	 * @param zone
	 *            时区
	 * @return
	 */
	public static String format(Date date, DateTypeEnum type, String zone) {
		return format(date, type.getFormat(), zone);
	}

	/**
	 * 将日期转换为string
	 * 
	 * @Title: format
	 * @Description: 将日期转换为string
	 * @param date
	 *            日期
	 * @param type
	 *            日期枚举类型
	 * @return
	 */
	public static String format(Date date, DateTypeEnum type) {
		return format(date, type.getFormat());
	}
}
