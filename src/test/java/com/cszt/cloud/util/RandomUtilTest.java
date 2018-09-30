package com.cszt.cloud.util;

import org.junit.Test;

public class RandomUtilTest {

	@Test
	public void testInteger(){
		System.out.println(RandomUtil.integer(1, 100));
	}
	
	@Test
	public void testNumber(){
		System.out.println(RandomUtil.number(10));
	}
	
	@Test
	public void testString(){
		System.out.println(RandomUtil.string(10));
	}
	
	@Test
	public void testMixString(){
		System.out.println(RandomUtil.mixString(10));
	}
	
	@Test
	public void testLowerString(){
		System.out.println(RandomUtil.lowerString(10));
	}
	
	@Test
	public void testUpperString(){
		System.out.println(RandomUtil.upperString(10));
	}
	
	@Test
	public void testZeroString(){
		System.out.println(RandomUtil.zeroString(10));
	}
	
	@Test
	public void testToFixdLengthString(){
		System.out.println(RandomUtil.toFixdLengthString(Long.parseLong(RandomUtil.number(6)), 10));
	}
}
