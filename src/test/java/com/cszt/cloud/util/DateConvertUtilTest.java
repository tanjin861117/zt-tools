package com.cszt.cloud.util;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

public class DateConvertUtilTest {

	@Test
	public void testFormat(){
		System.out.println(DateConvertUtil.format(new Date(), DateTypeEnum.DATE_FORMAT_02.getFormat()));
	}
	
	@Test
	public void testParse(){
		try {
			System.out.println(DateConvertUtil.parse("2018-09-25 11:47:14", DateTypeEnum.DATE_FORMAT_02).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
