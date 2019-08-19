package pers.utils.httpclientUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;
import pers.utils.httpclientUtils.common.Utils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpConfig {

    private HttpConfig() {
    }

    private HttpMethods method = HttpMethods.GET;
    private HttpClient client;
    private String url;

    /**
     * 设置RequestConfig
     */
    private RequestConfig requestConfig;
    /**
     * Header头信息
     */
    private Header[] headers;
    /**
     * 用于cookie操作
     */
    private HttpContext context;

    //body请求体
    private String body;

    /**
     * 是否返回response的headers
     */
    private boolean isReturnRespHeaders;
    /**
     * 输入输出编码
     */
    private String encoding = Charset.defaultCharset().displayName();

    /**
     * 输入编码
     */
    private String inenc;

    private HttpEntity httpEntity;

    /**
     * @param inenc 输入编码
     * @return 返回当前对象
     */
    public HttpConfig inenc(String inenc) {
        this.inenc = inenc;
        return this;
    }

    public String inenc() {
        return inenc == null ? encoding : inenc;
    }

    /**
     * 输出编码
     */
    private String outenc;

    /**
     * 解决多线程处理时，url被覆盖问题
     */
    private static final ThreadLocal<Map<String, Object>> maps = new ThreadLocal<Map<String, Object>>();//可以缩写new ThreadLocal<>();

    /**
     * @param map 传递参数
     * @return 返回当前对象
     */
    public HttpConfig map(Map<String, Object> map) {
//		synchronized (getClass()) {
//			if(this.map==null || map==null){
//				this.map = map;
//			}else {
//				this.map.putAll(map);;
//			}
//		}
        Map<String, Object> m = maps.get();
        if (m == null || map == null) {
            m = map;
        } else {
            m.putAll(map);
        }
        maps.set(m);
        return this;
    }

    public Map<String, Object> map() {
//		return map;
        return maps.get();
    }

    public static HttpConfig custom() {
        return new HttpConfig();
    }


    public String getUrl() {
        return url;
    }

    private HttpClient getClient() {
        return client;
    }

    /**
     * 上传文件时用到
     *
     * @param filePaths                     待上传文件所在路径
     * @param inputName                     即file input 标签的name值，默认为file
     * @param forceRemoveContentTypeChraset 是否强制一处content-type中设置的编码类型
     * @return 返回当前对象
     */
    public HttpConfig files(String[] filePaths, String inputName, boolean forceRemoveContentTypeChraset) {
//		synchronized (getClass()) {
//			if(this.map==null){
//				this.map= new HashMap<String, Object>();
//			}
//		}
//		map.put(Utils.ENTITY_MULTIPART, filePaths);
//		map.put(Utils.ENTITY_MULTIPART+".name", inputName);
//		map.put(Utils.ENTITY_MULTIPART+".rmCharset", forceRemoveContentTypeChraset);

        Map<String, Object> m = maps.get();
        if (m == null || m == null) {
            m = new HashMap<String, Object>();
        }
        m.put(Utils.ENTITY_MULTIPART, filePaths);
        m.put(Utils.ENTITY_MULTIPART + ".name", inputName);
        m.put(Utils.ENTITY_MULTIPART + ".rmCharset", forceRemoveContentTypeChraset);
        maps.set(m);
        return this;
    }


    public HttpConfig httpEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
        return this;
    }

    public HttpEntity httpEntity() {
        return httpEntity;
    }


    /**
     * @param client HttpClient对象
     * @return 返回当前对象
     */
    public HttpConfig client(HttpClient client) {
        this.client = client;
        return this;
    }

    public HttpClient client() {
        return this.getClient();
    }

    public String body() {
        return this.getBody();
    }

    public HttpConfig url(String url) {
        this.url = url;
        return this;
    }

    public String url() {
        return this.getUrl();
    }

    public HttpConfig body(String body) {
        this.body = body;
        return this;
    }

    public String getBody() {
        return body;
    }

    /**
     * @param method 请求方法
     * @return 返回当前对象
     */
    public HttpConfig method(HttpMethods method) {
        this.method = method;
        return this;
    }

    /**
     * @param context cookie操作相关
     * @return 返回当前对象
     */
    public HttpConfig context(HttpContext context) {
        this.context = context;
        return this;
    }

    /**
     * @param outenc 输出编码
     * @return 返回当前对象
     */
    public HttpConfig outenc(String outenc) {
        this.outenc = outenc;
        return this;
    }

    /**
     * Header头信息(是否返回response中的headers)
     *
     * @param headers             Header头信息
     * @param isReturnRespHeaders 是否返回response中的headers
     * @return 返回当前对象
     */
    public HttpConfig headers(Header[] headers, boolean isReturnRespHeaders) {
        this.headers = headers;
        this.isReturnRespHeaders = isReturnRespHeaders;
        return this;
    }

    /**
     * @param headers Header头信息
     * @return 返回当前对象
     */
    public HttpConfig headers(Header[] headers) {
        this.headers = headers;
        return this;
    }

    /**
     * 设置超时时间
     *
     * @param timeout 超市时间，单位-毫秒
     * @return 返回当前对象
     */
    public HttpConfig timeout(int timeout) {
        return timeout(timeout, true);
    }

    public HttpConfig timeout(int timeout, boolean redirectEnable) {
        // 配置请求的超时设置
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .setRedirectsEnabled(redirectEnable)
                .build();
        return timeout(config);
    }

    /**
     * 设置代理、超时时间、允许网页重定向等
     *
     * @param requestConfig 超时时间，单位-毫秒
     * @return 返回当前对象
     */
    public HttpConfig timeout(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
        return this;
    }

    public RequestConfig requestConfig() {
        return requestConfig;
    }

    public Header[] headers() {
        return headers;
    }

    public HttpMethods method() {
        return method;
    }

    public HttpContext context() {
        return context;
    }

    public boolean isReturnRespHeaders() {
        return isReturnRespHeaders;
    }

    public String outenc() {
        return outenc == null ? encoding : outenc;
    }


}
