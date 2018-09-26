package com.cszt.cloud.util;

import java.security.MessageDigest;

/**
 * sha加密工具类
 * 
 * @ClassName: Sha1Util
 * @Description: sha1加密工具类
 * @author: tanjin
 * @date:2017年12月27日 上午9:58:29
 */
public class ShaUtil {

	public static final String SHA = "SHA";

	public static final String SHA_224 = "SHA-224";
	
	public static final String SHA_256 = "SHA-256";
	
	public static final String SHA_384 = "SHA-384";

	public static final String SHA_512 = "SHA-512";

	/**
	 * sha加密
	 * 
	 * @Title: getSha
	 * @Description: sha加密
	 * @param str
	 * @param type
	 *            取值 SHA SHA-256 SHA-512
	 * @return
	 */
	public static String getSha(String str, String type) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance(type);
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
}
