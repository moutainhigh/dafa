package pers.utils.httpUtils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import pers.utils.httpclientUtils.HttpHeader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 以前的方法-已经启用
 */
public class Request {

    static CloseableHttpClient httpclient;
    static CloseableHttpResponse response = null;
    //static CookieStore cookieStore = null;
    static HttpPost httpPost = null;
    static HttpGet httpGet = null;
    static RequestConfig requestConfig;

    private static HttpClientContext context = new HttpClientContext();
    private static CookieStore cookieStore = new BasicCookieStore();
    private static BasicClientCookie cookie;

    static {
        // 设置请求超时时间
        requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).build();
        httpclient = SSLClientCustom.getHttpClinet();

        //初始化含cookie的context，
        cookie = new BasicClientCookie("JSESSIONID", "495847380F38F4991D72B42D2D46A7E4"); //JSESSIONID
        cookie.setVersion(0);
        try {
            cookie.setDomain(new URL("http://pt03.dafacloud-testCookie.com").getHost());//设置范围
        } catch (Exception e) {
            e.printStackTrace();
        }
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        context.setCookieStore(cookieStore);

    }


    /**
     * post 获取返回body
     * params : httpclient
     * return : String getBody
     */
    public static String getPostBody(String url, String body, Map<String, String> headers) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        //添加请求头
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
                httpPost.addHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
        }

        // 配置请求体
        StringEntity entity = new StringEntity(body, "UTF-8");
        httpPost.setEntity(entity);

        try {
            //执行请求
            response = httpclient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //StatusLine status = response.getStatusLine();
        //int state = status.getStatusCode();
        //if (state == HttpStatus.SC_OK) {
        // 返回值
        HttpEntity responseEntity = response.getEntity();
        response.getAllHeaders();
//        for(Header header :response.getAllHeaders()){
//            System.out.println(header.getName()+":"+header.getValue());
//        }
        String result = null;
        try {
            result = EntityUtils.toString(responseEntity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
     */
    public static String doPost(String url, String body, Map<String, String> headers) {
        return getPostBody(url, body, headers);
    }

    /**
     * 3.params :
     * return : body
     * 主要用在不需要登陆的请求
     */
    public static String doPost(String url, String body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("x-tenant-type", "1");//1直营,2渠道
        headers.put("x-tenant-code", "test");//站名test
        headers.put("x-manager-name", "55153432");//用户名
        headers.put("x-manager-id", "55153432");//用户id
        headers.put("x-is-system", "1");//平台账号1，其他是0
        headers.put("x-client-ip", "118.143.214.129");//ip

        headers.put("x-user-id", "83");//用户id
        headers.put("x-source-id", "1");//来源 1
        headers.put("x-user-name", "30117706");//用户名
        headers.put("x-user-type", "0");//正式0/测试1/遊客2
        //headers.put("x-source-id", "0");//
        headers.put("x-session-id", "41d5b92cb94e41f1aeb09665477fde94");
        return getPostBody(url, body, headers);
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
     */
    public static String doGetBody(String url, Map<String, String> headers) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        // 设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(50000).setConnectionRequestTimeout(50000)
                .setSocketTimeout(50000).build();
        httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        //httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
                String key = i.next();
                httpGet.addHeader(key, headers.get(key));
            }
        }
        try {
            // 执行get请求
            response = httpclient.execute(httpGet);
            // 获取返回状态行
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            //if (state == HttpStatus.SC_OK) {
            // 返回值
            HttpEntity responseEntity = response.getEntity();
            String jsonString = EntityUtils.toString(responseEntity);
            return jsonString;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get请求
     * params : Cookie</br>
     * return : String
     */
    public static String doGet(String url, Map<String, String> headers) {
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
        /*headers.put("x-tenant-type", "1");//1直营,2渠道
        headers.put("x-tenant-code", "testCookie");//站名test
        headers.put("x-manager-name", "55153432");//用户名
        headers.put("x-manager-id", "55153432");//用户id
        headers.put("x-is-system", "1");//平台账号1，其他是0
        headers.put("x-client-ip", "134.159.225.3");//ip

        headers.put("x-userCenter-id", "55153432");
        headers.put("x-source-id", "1");
        headers.put("x-userCenter-name", "88467689");
        headers.put("x-session-id", "21b93e1b29d54f9186e68d3b4fb02c19");*/
        return doGetBody(url, headers);
    }

    /**
     * 上传文件
     */
    public static String uploadFIle(String url, String body, Map<String, String> headers) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
                httpPost.addHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
        }
        // 配置请求体
        StringEntity entity = new StringEntity(body, "UTF-8");
        httpPost.setEntity(entity);
        //File file = new File("/Users/duke/Downloads/quantity.xlsx");
        String localFileName = "/Users/duke/Downloads/quantity.xlsx";
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        //multipartEntityBuilder.addBinaryBody("file",file);
        //multipartEntityBuilder.addTextBody("comment", "this is comment");
        multipartEntityBuilder.addPart("file", new FileBody(new File(localFileName)));
        multipartEntityBuilder.addPart("timeStamp", new StringBody(String.valueOf(Math.random()), ContentType.MULTIPART_FORM_DATA));
        multipartEntityBuilder.addPart("dictionId", new StringBody("401", ContentType.MULTIPART_FORM_DATA));
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        try {
            response = httpclient.execute(httpPost, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StatusLine status = response.getStatusLine();
        // 返回值
        HttpEntity responseEntity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(responseEntity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public static void main(String[] args) {
        String url = "http://pt03.dafacloud-testCookie.com/v1/transaction/importManualRecord";
        Map<String, String> map = new HashMap<>();
        //map.put("Content-Type","multipart/form-data; boundary=----WebKitFormBoundaryUepX1Zeys0Vu54Bk");
        map.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
        String result = uploadFIle(url, "", map);
        System.out.println(result);
    }
}
