package com.cszt.cloud.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * json帮助工具类
 * 
 * @ClassName: JsonUtil
 * @Description: json帮助工具类
 * @author: tanjin
 * @date:2018年9月25日 下午3:34:52
 */
public class JsonUtil {

	private JsonUtil() {

	}

	/**
	 * 将对象转换为json字符串
	 * 
	 * @Title: toString
	 * @Description: 将对象转换为json字符串
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return JSON.toJSONString(obj);
	}

	/**
	 * 将json字符串转换为对象
	 * 
	 * @Title: parse
	 * @Description: 将json字符串转换为对象
	 * @param json
	 * @param T
	 * @return
	 */
	public static <T> T parse(String json, Class<T> T) {
		return JSON.parseObject(json, T);
	}

	/**
	 * 将json字符串转换为Map
	 * 
	 * @Title: parseMap
	 * @Description: 将json字符串转换为Map
	 * @param json
	 * @return
	 */
	public static Map<String, String> parseMap(String json) {
		return JSON.parseObject(json, new TypeReference<Map<String, String>>() {});
	}

	/**
	 * 将json字符串转换为list
	 * 
	 * @Title: parseList
	 * @Description: 将json字符串转换为list
	 * @param json
	 * @return
	 */
	public static List<Map<String, Object>> parseList(String json) {
		return JSON.parseObject(json, new TypeReference<List<Map<String, Object>>>() {});
	}
	
	/**
	 * 将json字符串转换为list
	 * @Title: parseList   
	 * @Description: 将json字符串转换为list  
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseList(String json, Class<T> clazz){
		return JSON.parseArray(json, clazz);
	}
}
