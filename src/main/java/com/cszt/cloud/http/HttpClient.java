package com.cszt.cloud.http;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cszt.cloud.util.EmptyUtil;

import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * http调用客户端
 * 
 * @ClassName: HttpClient
 * @Description: http调用客户端
 * @author: tanjin
 * @date:2018年9月26日 下午5:46:13
 */
public class HttpClient {

	Logger log = LoggerFactory.getLogger(this.getClass());
	private OkHttpClient client = null;

	public HttpClient() {
		this(HttpConstants.DEFAULT_CONNECT_TIME, HttpConstants.DEFAULT_READ_TIME, HttpConstants.DEFAULT_CONNECT_POOL);
	}

	public HttpClient(int connect_time, int read_time) {
		this(connect_time, read_time, HttpConstants.DEFAULT_CONNECT_POOL);
	}

	public HttpClient(int connect_time, int read_time, int connect_pool) {
		client = new OkHttpClient.Builder()
				.connectionPool(new ConnectionPool(connect_pool, HttpConstants.DEFAULT_EXPRIE_TIME, TimeUnit.MINUTES))
				.socketFactory(createSSLSocketFactory())
				.hostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				}).readTimeout(read_time, TimeUnit.SECONDS).connectTimeout(connect_time, TimeUnit.SECONDS).build();
	}

	/**
	 * get请求
	 * 
	 * @Title: get
	 * @Description: get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String get(String url) throws Exception {
		return get(url, null);
	}

	/**
	 * get请求
	 * 
	 * @Title: get
	 * @Description: get请求
	 * @param url
	 * @param charset
	 * @param queryMap
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String get(String url, String charset, Map<String, String> queryMap, Map<String, String> headerMap)
			throws Exception {
		HttpBuild build = new HttpBuild();
		build.setUrl(url);
		build.setRequestCharset(charset);
		build.setQueryMap(queryMap);
		build.setHeaderMap(headerMap);
		build.setMethod(HttpConstants.GET);
		return execute(build);
	}

	/**
	 * get请求
	 * 
	 * @Title: get
	 * @Description: get请求
	 * @param url
	 * @param queryMap
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Map<String, String> queryMap) throws Exception {
		return get(url, HttpConstants.DEFAULT_CHARSET, queryMap, null);
	}

	/**
	 * get请求
	 * 
	 * @Title: get
	 * @Description: get请求
	 * @param url
	 * @param queryMap
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Map<String, String> queryMap, Map<String, String> headerMap) throws Exception {
		return get(url, HttpConstants.DEFAULT_CHARSET, queryMap, headerMap);
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String post(String url) throws Exception {
		return post(url, HttpConstants.DEFAULT_CHARSET, "");
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String post(String url, Object obj) throws Exception {
		return post(url, JSON.toJSONString(obj));
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param charset
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String post(String url, String charset, Object obj) throws Exception {
		return post(url, charset, JSON.toJSONString(obj));
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param charset
	 * @param obj
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String post(String url, String charset, Object obj, Map<String, String> headerMap) throws Exception {
		return post(url, charset, JSON.toJSONString(obj), headerMap);
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param charset
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String post(String url, String charset, String data) throws Exception {
		return post(url, charset, data, HttpConstants.DEFAULT_MEDIA_TYPE, null);
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param charset
	 * @param data
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String post(String url, String charset, String data, Map<String, String> headerMap) throws Exception {
		return post(url, charset, data, HttpConstants.DEFAULT_MEDIA_TYPE, headerMap);
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param charset
	 * @param data
	 * @param mediaType
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String post(String url, String charset, String data, String mediaType, Map<String, String> headerMap)
			throws Exception {
		HttpBuild build = new HttpBuild();
		build.setUrl(url);
		build.setRequestCharset(charset);
		build.setData(data);
		build.setHeaderMap(headerMap);
		build.setMethod(HttpConstants.POST);
		build.setMediaType(mediaType);
		return execute(build);
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param data
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String post(String url, String data, Map<String, String> headerMap) throws Exception {
		return post(url, HttpConstants.DEFAULT_CHARSET, data, headerMap);
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String post(String url, String data) throws Exception {
		return post(url, HttpConstants.DEFAULT_CHARSET, data);
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param formMap
	 * @return
	 * @throws Exception
	 */
	public String post(String url, Map<String, String> formMap) throws Exception {
		return post(url, HttpConstants.DEFAULT_CHARSET, formMap, null);
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param formMap
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String post(String url, Map<String, String> formMap, Map<String, String> headerMap) throws Exception {
		return post(url, HttpConstants.DEFAULT_CHARSET, formMap, headerMap);
	}

	/**
	 * post请求
	 * 
	 * @Title: post
	 * @Description: post请求
	 * @param url
	 * @param charset
	 * @param formMap
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String post(String url, String charset, Map<String, String> formMap, Map<String, String> headerMap)
			throws Exception {
		String data = "";
		if (EmptyUtil.isNotEmpty(formMap)) {
			data = formMap.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
					.collect(Collectors.joining("&"));
		}
		return post(url, charset, data, HttpConstants.X_WWW_FORM_URLENCODED, headerMap);
	}

	/**
	 * put请求
	 * 
	 * @Title: put
	 * @Description: put请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String put(String url) throws Exception {
		return put(url, "");
	}

	/**
	 * put请求
	 * 
	 * @Title: put
	 * @Description: put请求
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String put(String url, String data) throws Exception {
		return put(url, HttpConstants.DEFAULT_CHARSET, data);
	}

	/**
	 * put请求
	 * 
	 * @Title: put
	 * @Description: put请求
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String put(String url, String data, Map<String, String> headerMap) throws Exception {
		return put(url, HttpConstants.DEFAULT_CHARSET, data, headerMap);
	}

	/**
	 * put请求
	 * 
	 * @Title: put
	 * @Description: put请求
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String put(String url, Object data) throws Exception {
		return put(url, JSON.toJSONString(data));
	}

	/**
	 * put请求
	 * 
	 * @Title: put
	 * @Description: put请求
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String put(String url, Object data, Map<String, String> headerMap) throws Exception {
		return put(url, JSON.toJSONString(data), headerMap);
	}

	/**
	 * put请求
	 * 
	 * @Title: put
	 * @Description: put请求
	 * @param url
	 * @param charset
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String put(String url, String charset, String data) throws Exception {
		return put(url, charset, data, null);
	}

	/**
	 * put请求
	 * 
	 * @Title: put
	 * @Description: put请求
	 * @param url
	 * @param charset
	 * @param data
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String put(String url, String charset, String data, Map<String, String> headerMap) throws Exception {
		HttpBuild build = new HttpBuild();
		build.setUrl(url);
		build.setRequestCharset(charset);
		build.setData(data);
		build.setHeaderMap(headerMap);
		build.setMethod(HttpConstants.PUT);
		return execute(build);
	}

	/**
	 * delete请求
	 * 
	 * @Title: delete
	 * @Description: delete请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String delete(String url) throws Exception {
		return delete(url, "");
	}

	/**
	 * delete请求
	 * 
	 * @Title: delete
	 * @Description: delete请求
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String delete(String url, String data) throws Exception {
		return delete(url, data, null);
	}

	/**
	 * delete请求
	 * 
	 * @Title: delete
	 * @Description: delete请求
	 * @param url
	 * @param data
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String delete(String url, String data, Map<String, String> headerMap) throws Exception {
		return delete(url, HttpConstants.DEFAULT_CHARSET, data, headerMap);
	}

	/**
	 * delete请求
	 * 
	 * @Title: delete
	 * @Description: delete请求
	 * @param url
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String delete(String url, Map<String, String> headerMap) throws Exception {
		return delete(url, null, headerMap);
	}

	/**
	 * delete请求
	 * 
	 * @Title: delete
	 * @Description: delete请求
	 * @param url
	 * @param charset
	 * @param data
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String delete(String url, String charset, String data, Map<String, String> headerMap) throws Exception {
		HttpBuild build = new HttpBuild();
		build.setUrl(url);
		build.setRequestCharset(charset);
		build.setData(data);
		build.setHeaderMap(headerMap);
		build.setMethod(HttpConstants.DELETE);
		return execute(build);
	}

	/**
	 * patch请求
	 * 
	 * @Title: patch
	 * @Description: patch请求
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String patch(String url, String data) throws Exception {
		return patch(url, data, null);
	}

	/**
	 * patch请求
	 * 
	 * @Title: patch
	 * @Description: patch请求
	 * @param url
	 * @param data
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String patch(String url, String data, Map<String, String> headerMap) throws Exception {
		return patch(url, HttpConstants.DEFAULT_CHARSET, data, headerMap);
	}

	/**
	 * patch请求
	 * 
	 * @Title: patch
	 * @Description: patch请求
	 * @param url
	 * @param charset
	 * @param data
	 * @param headerMap
	 * @return
	 * @throws Exception
	 */
	public String patch(String url, String charset, String data, Map<String, String> headerMap) throws Exception {
		HttpBuild build = new HttpBuild();
		build.setUrl(url);
		build.setRequestCharset(charset);
		build.setData(data);
		build.setHeaderMap(headerMap);
		build.setMethod(HttpConstants.PATCH);
		return execute(build);
	}

	/**
	 * 执行http请求
	 * 
	 * @Title: execute
	 * @Description: 执行http请求
	 * @param httpBuild
	 * @return
	 * @throws Exception
	 */
	private String execute(HttpBuild httpBuild) throws Exception {
		String url = httpBuild.getUrl();
		Request.Builder builder = new Request.Builder();
		if (EmptyUtil.isNotEmpty(httpBuild.getQueryMap())) {
			String queryParams = httpBuild.getQueryMap().entrySet().stream()
					.map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
					.collect(Collectors.joining("&"));
			url = String.format("%s%s%s", url, url.contains("?") ? "&" : "?", queryParams);
		}

		builder.url(url);

		if (EmptyUtil.isNotEmpty(httpBuild.getHeaderMap())) {
			httpBuild.getHeaderMap().forEach(builder::addHeader);
		}

		String method = httpBuild.getMethod().toUpperCase();
		String mediaType = String.format("%s;charset=%s", httpBuild.getMediaType(), httpBuild.getRequestCharset());

		if (HttpConstants.GET.equals(method)) {
			log.info("httpclient get method ,request url:{}", url);
			builder.get();
		} else if (ArrayUtils.contains(
				new String[] { HttpConstants.POST, HttpConstants.PUT, HttpConstants.DELETE, HttpConstants.PATCH },
				method)) {
			RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType), httpBuild.getData());
			builder.method(method, requestBody);
			log.info("httpclient {} method, request data:{}", method, httpBuild.getData());
		} else {
			log.info("http method:{} not support!", method);
			throw new NotSupportedException(String.format("http method:%s not support!", method));
		}
		Response response = client.newCall(builder.build()).execute();
		byte[] bytes = response.body().bytes();
		String result = new String(bytes, httpBuild.getResponseCharset());
		log.info("httpclient return message:{}", result);
		return result;
	}

	/**
	 * https受信任工厂
	 * @Title: createSSLSocketFactory   
	 * @Description: https受信任工厂  
	 * @return
	 */
	private SSLSocketFactory createSSLSocketFactory() {
		SSLSocketFactory ssfFactory = null;
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { new TrustAllCerts() }, new SecureRandom());
			ssfFactory = sc.getSocketFactory();
		} catch (Exception e) {
		}
		return ssfFactory;
	}
}
