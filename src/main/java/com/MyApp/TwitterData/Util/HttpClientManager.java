package com.MyApp.TwitterData.Util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;

/**
 * HttpClient 工具类
 * @author Love
 *
 */
public class HttpClientManager {
	private HttpClientManager() {
	}

	private static class HttpClientManagerInstance {
		public static final HttpClientManager instance = new HttpClientManager();
	}

	public static HttpClientManager getInstance() {
		return HttpClientManagerInstance.instance;
	}

	private PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	private HttpContext context = HttpClientContext.create();
	private BasicCookieStore cookieStore = new BasicCookieStore();

	public BasicCookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(BasicCookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}
	/**
	 * 线程池管理器
	 * @return
	 */
	public PoolingHttpClientConnectionManager getManager() {
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
		return cm;
	}
	/**
	 * 上下文管理器
	 * @return
	 */
	public HttpContext getHttpContext() {
		return context;
	}
	/**
	 * ssl管理器
	 * @return
	 */
	public SSLConnectionSocketFactory getssl() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return sslsf;
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 请求设置器
	 * @return
	 */
	public RequestConfig getRequestConfig(){
		RequestConfig requestConfig = RequestConfig.custom()
		.setConnectionRequestTimeout(20000)
		.setSocketTimeout(20000)
		.setConnectTimeout(20000)
		.build();
		return requestConfig;
	}
}
