package com.cszt.cloud.http;

import java.util.Map;

/**
 * http请求参数构造器
 * 
 * @ClassName: HttpBuild
 * @Description: http请求参数构造器
 * @author: tanjin
 * @date:2018年9月26日 下午4:19:56
 */
public class HttpBuild {

	private String url;
	private String method = HttpConstants.DEFAULT_METHOD;
	private String data;
	private String mediaType = HttpConstants.DEFAULT_MEDIA_TYPE;
	private Map<String, String> queryMap;
	private Map<String, String> headerMap;
	private String requestCharset = HttpConstants.DEFAULT_CHARSET;

	private String responseCharset = HttpConstants.DEFAULT_CHARSET;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public Map<String, String> getQueryMap() {
		return queryMap;
	}

	public void setQueryMap(Map<String, String> queryMap) {
		this.queryMap = queryMap;
	}

	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	public String getRequestCharset() {
		return requestCharset;
	}

	public void setRequestCharset(String requestCharset) {
		this.requestCharset = requestCharset;
	}

	public String getResponseCharset() {
		return responseCharset;
	}

	public void setResponseCharset(String responseCharset) {
		this.responseCharset = responseCharset;
	}
}
