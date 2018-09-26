package com.cszt.cloud.http;

/**
 * http常量
 * @ClassName: HttpConstants   
 * @Description: http常量  
 * @author: tanjin  
 * @date:2018年9月26日 下午4:10:21
 */
public interface HttpConstants {
	/**
	 * method
	 */
	public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";
    public final static String PATCH = "PATCH";

    /**
     * charset
     */
    public final static String UTF8 = "UTF-8";
    public final static String GBK = "GBK";

    /**
     * http default params
     */
    public final static String DEFAULT_CHARSET = UTF8;
    public final static String DEFAULT_METHOD = GET;
    public final static String DEFAULT_MEDIA_TYPE = "application/json";
    
    public final static int DEFAULT_CONNECT_TIME = 30;
    public final static int DEFAULT_READ_TIME = 30;
    public final static int DEFAULT_CONNECT_POOL = 10;
    public final static int DEFAULT_EXPRIE_TIME = 10;
    
    public final static String X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
}
