package com.cszt.cloud.util;

import java.util.Collection;
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
	public static boolean isEmpty(Map map) {
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
	public static boolean isNotEmpty(Map map) {
		return !isEmpty(map);
	}

	/**
	 * list为空判断，list有值则返回false，没有则返回true
	 * 
	 * @Title: isListEmpty
	 * @Description: list为空判断，list有值则返回false，没有则返回true
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List list) {
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
	public static boolean isNotEmpty(List list) {
		return !isEmpty(list);
	}

	/**
	 * 对象为空判断
	 * 
	 * @Title: isObjEmpty
	 * @Description: 对象为空判断
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
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
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	
	/**
	 * string为空判断
	 * @Title: isStrEmpty   
	 * @Description: string为空判断
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
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
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	/**
	 * string不为空判断
	 * @Title: isNotStrEmpty   
	 * @Description: string不为空判断  
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String... str){
		for(String s : str){
			if(isEmpty(s)){
				return false;
			}
		}
		return true;
	}
	
	 /**
	  * 集合为空判断
	  * @Title: isEmpty   
	  * @Description: 集合为空判断  
	  * @param col
	  * @return
	  */
    public final static boolean isEmpty(Collection col) {
        return col == null || col.isEmpty();
    }

    /**
     * 集合不为空判断
     * @Title: isNotEmpty   
     * @Description: 集合不为空判断  
     * @param cols
     * @return
     */
    public final static boolean isNotEmpty(Collection... cols) {
        for (Collection c : cols) {
            if (isEmpty(c)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 集合不为空判断
     * @Title: isNotEmpty   
     * @Description: 集合不为空判断  
     * @param cols
     * @return
     */
    public final static boolean isNotEmpty(Collection cols) {
        return !isEmpty(cols);
    }
}
