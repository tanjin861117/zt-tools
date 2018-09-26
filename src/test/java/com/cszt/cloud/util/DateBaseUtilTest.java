package com.cszt.cloud.util;

import java.util.Date;

import org.junit.Test;

public class DateBaseUtilTest {

	@Test
	public void testDateAdd(){
		System.out.println(DateBaseUtil.dateAdd(DateBaseUtil.DATE, -2, new Date()));
	}
	
	@Test
	public void testUtcToLocal(){
		System.out.println(DateBaseUtil.utcToLocal("2018-01-24T09:54:22Z", "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void testLocalToUtc(){
		System.out.println(DateBaseUtil.localToUTC("2018-01-24 09:54:22"));
	}
}
