package com.cszt.cloud.util;

import java.util.List;
import java.util.Map;

/**
 * 空对象判断帮助类
 * 
 * @ClassName: EmptyUtil
 * @Description: 空对象判断帮助类
 * @author: tanjin
 * @date:2018年9月26日 上午10:38:43
 */
@SuppressWarnings("rawtypes")
public class EmptyUtil {

	private EmptyUtil() {

	}

	/**
	 * map为空判断，map有键值则返回false，没有则返回true
	 * 
	 * @Title: isMapEmpty
	 * @Description: map为空判断，map有键值则返回false，没有则返回true
	 * @param map
	 * @return
	 */
	public static boolean isMapEmpty(Map map) {
		if (map != null && !map.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * map为空判断，map有键值则返回true，没有则返回false
	 * 
	 * @Title: isMapEmpty
	 * @Description: map为空判断，map有键值则返回true，没有则返回false
	 * @param map
	 * @return
	 */
	public static boolean isNotMapEmpty(Map map) {
		return !isMapEmpty(map);
	}

	/**
	 * list为空判断，list有值则返回false，没有则返回true
	 * 
	 * @Title: isListEmpty
	 * @Description: list为空判断，list有值则返回false，没有则返回true
	 * @param list
	 * @return
	 */
	public static boolean isListEmpty(List list) {
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * list为空判断，list有值则返回true，没有则返回false
	 * 
	 * @Title: isListEmpty
	 * @Description: list为空判断，list有值则返回true，没有则返回false
	 * @param list
	 * @return
	 */
	public static boolean isNotListEmpty(List list) {
		return !isListEmpty(list);
	}

	/**
	 * 对象为空判断
	 * 
	 * @Title: isObjEmpty
	 * @Description: 对象为空判断
	 * @param obj
	 * @return
	 */
	public static boolean isObjEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/**
	 * 对象为空判断
	 * 
	 * @Title: isObjEmpty
	 * @Description: 对象为空判断
	 * @param obj
	 * @return
	 */
	public static boolean isNotObjEmpty(Object obj) {
		return !isObjEmpty(obj);
	}
	
	/**
	 * string为空判断
	 * @Title: isStrEmpty   
	 * @Description: string为空判断
	 * @param str
	 * @return
	 */
	public static boolean isStrEmpty(String str){
		int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
	}
	
	/**
	 * string不为空判断
	 * @Title: isNotStrEmpty   
	 * @Description: string不为空判断  
	 * @param str
	 * @return
	 */
	public static boolean isNotStrEmpty(String str){
		return !isStrEmpty(str);
	}
}
