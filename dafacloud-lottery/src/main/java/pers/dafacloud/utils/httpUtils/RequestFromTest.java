package pers.dafacloud.utils.httpUtils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RequestFromTest {

	static CloseableHttpClient httpclient;
	static CloseableHttpResponse response = null;
	//static CookieStore cookieStore = null;
	static HttpPost httpPost = null;
	static HttpGet httpGet = null;
	static RequestConfig requestConfig;

	static {
		// 设置请求超时时间
		requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000)
				.setSocketTimeout(30000).build();
		httpclient = SSLClientCustom.getHttpClinet();
	}

	/**
	 * post 获取返回body
	 * params : httpclient
	 * return : String getBody
	 * */
	public static String getBody(String url,String body,Map<String, String> headers)  {
		if (StringUtils.isEmpty(url)) {
            return null;
        }
		httpPost=new HttpPost(url);
		httpPost.setConfig(requestConfig);
		if (MapUtils.isNotEmpty(headers)) {
			for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
                httpPost.addHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
		}
		
		// 配置请求体
		StringEntity entity = new StringEntity(body, "UTF-8");
		httpPost.setEntity(entity);
		try {
			response = httpclient.execute(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StatusLine status = response.getStatusLine();
		int state = status.getStatusCode();
		if (state == HttpStatus.SC_OK) {
			// 返回值
			HttpEntity responseEntity = response.getEntity();
			String result = null;
			try {
				result = EntityUtils.toString(responseEntity,"utf-8");
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
			return result;
		} else {
			return null;
		}
		//return response;
	}
	
	/**
	 *1.params : 
	 * return : body,cookie
	 * 主要用在登陆
	 * */
	/*public static Map<String, Object> doPost(String url,String body,Map<String, String> headers) {
//		cookieStore =new BasicCookieStore();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String result = getBody( url, body, headers);
		List<Cookie> cookies = cookieStore.getCookies();
		responseMap.put("cookies", cookies);
		responseMap.put("body", result);
		return responseMap;
	}*/
	/**
	 * 2.params : cookie
	 * return : body
	 * 主要用在需要依赖登陆的请求
	 * */
	public static String doPost(String url,String body,Map<String, String> headers) {
		return getBody( url, body, headers);
	}
	/**
	 * 3.params : 
	 * return : body
	 * 主要用在不需要登陆的请求
	 * */
	public static String doPost(String url,String body) {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		return getBody( url, body, headers);
	}
	
	/**
	 * 4.入参带cookie 返回cookie
	 * 目前没用到
	 * */
	/*public static Map<String, Object> doPost(String url,String body,Map<String, String> headers,Cookie cookie) {
		cookieStore =new BasicCookieStore();
    	cookieStore.addCookie(cookie);
		httpclient =SSLClientCustom.getHttpClinet(cookieStore);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		String result="";
		result = getBody( url, body, headers, httpclient);
		List<Cookie> cookies = cookieStore.getCookies();
		responseMap.put("cookies", cookies);
		responseMap.put("body", result);
		return responseMap;
	}*/
	
	/**
	 * get返回body
	 * */
	public static String doGetBody(String url , Map<String, String> headers) {
		if (StringUtils.isEmpty(url)) {
            return null;
        }
		// 设置请求超时时间
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(50000).setConnectionRequestTimeout(50000)
				.setSocketTimeout(50000).build();
		httpGet=new HttpGet(url);
		httpGet.setConfig(requestConfig);
		
		//httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		if (headers != null) {
			Set<String> keys = headers.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = (String) i.next();
				httpGet.addHeader(key, headers.get(key));
			}
		}
		try {
			// 执行get请求
			response = httpclient.execute(httpGet);
			// 获取返回状态行
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_OK) {
				// 返回值
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			} else {
				return ("请求返回:" + state);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * get请求
	 * params : Cookie</br>
	 * return : String
	 * */
	public static String doGet(String url , Map<String, String> headers) {
		return doGetBody(url, headers);
	}
	
	
	
	/**
	 * 执行Get请求
	 * params : </br>
	 * return : String
	 */
	public static String doGet(String url) {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		return doGetBody(url, headers);
	}
}
