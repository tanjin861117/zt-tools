package com.cszt.cloud.util;

/**
 * 日期格式枚举,内含常用日期格式
 * @ClassName: DateTypeEnum   
 * @Description: 日期格式枚举  
 * @author: tanjin  
 * @date:2018年9月25日 上午11:01:44
 */
public enum DateTypeEnum {

	/**
	 * yyyyMMddHHmmss
	 */
	DATE_FORMAT_01("yyyyMMddHHmmss"),
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	DATE_FORMAT_02("yyyy-MM-dd HH:mm:ss"),
	/**
	 * yyyyMMddHHmmssSSS
	 */
	DATE_FORMAT_03("yyyyMMddHHmmssSSS"),
	/**
	 * yyyy-MM-dd HH:mm:ss SSS
	 */
	DATE_FORMAT_04("yyyy-MM-dd HH:mm:ss.SSS"),
	/**
	 * yyyy-MM-dd
	 */
	DATE_FORMAT_05("yyyy-MM-dd"),
	/**
	 * yyyyMMdd
	 */
	DATE_FORMAT_06("yyyyMMdd"),
	/**
	 * yyyy-MM-dd HH:mm
	 */
	DATE_FORMAT_07("yyyy-MM-dd HH:mm"),
	/**
	 * yyyyMMdd HH:mm
	 */
	DATE_FORMAT_08("yyyyMMdd HH:mm"),
	/**
	 * HH:mm:ss
	 */
	DATE_FORMAT_09("HH:mm:ss");
	
	private String format;
	
	public String getFormat() {
		return format;
	}

	private DateTypeEnum(String format){
		this.format = format;
	}
	
}
