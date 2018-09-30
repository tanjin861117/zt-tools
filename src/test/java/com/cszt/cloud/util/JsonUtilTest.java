package com.cszt.cloud.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JsonUtilTest {

	@Test
	public void testJsonString(){
		Map<String,String> map = new HashMap<>();
		map.put("test", "test");
		System.out.println(JsonUtil.toString(map));
	}
	
	@Test
	public void testParse(){
		String json = "{\"test\":\"test\"}";
		System.out.println(JsonUtil.parseMap(json));
	}
}
