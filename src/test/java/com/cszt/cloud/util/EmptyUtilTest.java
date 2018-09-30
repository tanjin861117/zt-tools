package com.cszt.cloud.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class EmptyUtilTest {

	@Test
	public void testIsEmpty(){
		System.out.println(EmptyUtil.isEmpty(new ArrayList<>()));
		System.out.println(EmptyUtil.isEmpty(new HashMap<>()));
		System.out.println(EmptyUtil.isEmpty(""));
	}
	
	@Test
	public void testIsNotEmpty(){
		List<String> list = new ArrayList<>();
		list.add("test");
		System.out.println(EmptyUtil.isNotEmpty(list));
		Map<String,String> map = new HashMap<>();
		map.put("test", "test");
		System.out.println(EmptyUtil.isNotEmpty(map));
		System.out.println(EmptyUtil.isNotEmpty("test"));
		System.out.println(EmptyUtil.isNotEmpty(new String[]{"test","aaa"}));
	}
}
