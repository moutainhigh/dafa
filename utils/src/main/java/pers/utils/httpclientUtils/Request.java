package pers.utils.httpclientUtils;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import pers.utils.httpclientUtils.common.Utils;
import pers.utils.httpclientUtils.exception.HttpProcessException;
import pers.utils.httpclientUtils.SSLClientCustom.SSLProtocolVersion;
import pers.utils.logUtils.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Request {

    //默认采用的http协议的HttpClient对象
    private static HttpClient client4HTTP;

    //默认采用的https协议的HttpClient对象
    private static HttpClient client4HTTPS;
    private static HttpConfig httpConfig = HttpConfig.custom();
    private static RequestConfig defaultRequestConfig;
    //private static HttpClientBuilder httpClientBuilder;

    static {

        try {
            //设置代理IP、端口、协议（请分别替换）
            /*HttpHost proxy = new HttpHost("192.168.8.30", 8080, "http");//dafa windows主机
            //HttpHost proxy = new HttpHost("127.0.0.1", 9876, "http");
            //把代理设置到请求配置*/
            defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(6000)//设置连接超时时间
                    .setSocketTimeout(6000)//设置读取超时时间
                    //.setProxy(proxy) //设置代理
                    .build();

            client4HTTP = HttpClientCustom.custom()
                    .pool(1000, 800)
                    //.setDefaultCookieStore(new BasicCookieStore())
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();

            //httpClientBuilder.setDefaultCookieStore(config.cookieStore());
            client4HTTPS = HttpClientCustom.custom()
                    .sslpv(SSLProtocolVersion.TLSv1_2)
                    .ssl()
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .build();

            httpConfig.headers(HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0")
                    .build());
        } catch (Exception e) {
            System.out.println("创建https协议的HttpClient对象出错：{}");
        }
    }

    private static void create(HttpConfig config) throws HttpProcessException {
        if (config.client() == null) {//如果为空，设为默认client对象
            if (config.url().toLowerCase().startsWith("https://")) {
                config.client(client4HTTPS);
            } else {
                config.client(client4HTTP);
            }
        }
    }

    //get 默认配置
    public static String get(String url) throws Exception {
        return get(httpConfig.url(url));
    }

    //传入cookie
    public static String get(String url, HttpContext httpContext) throws Exception {
        return get(httpConfig.url(url).context(httpContext));
    }

    /**
     * 以Get方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回结果
     * @throws HttpProcessException http处理异常
     */
    public static String get(HttpConfig config) throws HttpProcessException {
        //System.out.println(config.url());
        return send(config.method(HttpMethods.GET));
    }

    /**
     * 以Post方式，请求资源或服务
     * <p>
     * //* @param client		client对象
     * //* @param url			资源地址
     * //* @param headers		请求头信息
     * //* @param parasMap		请求参数
     * //* @param context		http上下文，用于cookie操作
     * //* @param encoding		编码
     *
     * @return 返回处理结果
     * @throws HttpProcessException http处理异常
     */
    /*public static String post(HttpClient client, String url, Header[] headers, HttpContext context) throws HttpProcessException {
        return post(HttpConfig.custom().client(client).url(url).headers(headers).context(context));
    }*/
    //使用使用默认header的post
    public static String post(String url, String body) throws Exception {
        return body + "," + post(httpConfig.url(url).body(body));
    }

    //自定义header的psot
    public static String post(String url, String body, Header[] headers) throws Exception {
        return post(httpConfig.url(url).body(body).headers(headers));
    }

    /**
     * 以Post方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws HttpProcessException http处理异常
     */
    public static String post(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.POST));
    }

    /**
     * 请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws HttpProcessException http处理异常
     */
    public static String send(HttpConfig config) throws HttpProcessException {
        return fmt2String(execute(config), config.outenc());
    }

    /**
     * 请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回HttpResponse对象
     * @throws HttpProcessException http处理异常
     */
    private static HttpResponse execute(HttpConfig config) throws HttpProcessException {
        create(config);//获取链接 //根据链接创建client4HTTPS 或者 client4HTTP
        HttpResponse resp = null;
        try {
            //创建请求对象
            //请求地址打印------------------------------------------------------------------------------------------------
            //Log.info(String.format("%s请求地址：%s", config.method(), config.url()));
            HttpRequestBase request = getRequest(config.url(), config.method()); //HttpGet HttpPost

            //设置超时
            request.setConfig(config.requestConfig());

            //设置header信息
            request.setHeaders(config.headers());

            //判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) { // post
                List<NameValuePair> nvps = new ArrayList<>();
                //if(request.getClass()==HttpGet.class) { //Get请求
                //检测url中是否存在参数
                //注：只有get请求，才自动截取url中的参数，post等其他方式，不再截取
                //config.url(Utils.checkHasParas(config.url(), nvps, config.inenc()));
                //}
                if (config.httpEntity() == null) {
                    //装填参数
                    HttpEntity entity;
                    if (config.map() == null) {
                        //请求body打印------------------------------------------------------------------------------------------------
                        //Log.info(config.body());
                        entity = new StringEntity(config.body(), "UTF-8");
                    } else {
                        entity = Utils.map2HttpEntity(nvps, config.map(), config.inenc());
                    }
                    ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
                } else {
                    ((HttpEntityEnclosingRequestBase) request).setEntity(config.httpEntity());
                }

                //设置参数到请求对象中


                /*Utils.info("请求地址："+config.url());
                if(nvps.size()>0){
                    Utils.info("请求参数："+nvps.toString());
                }
                if(config.json()!=null){
                    Utils.info("请求参数："+config.json());
                }*/
            } else {  //get请求
                int idx = config.url().indexOf("?");
                //System.out.println("GET请求地址："+config.url().substring(0, (idx>0 ? idx : config.url().length())));
                //Utils.info("GET请求地址："+config.url().substring(0, (idx>0 ? idx : config.url().length())));
                if (idx > 0) {
                    //System.out.println("GET请求参数："+config.url().substring(idx+1));
                    //Utils.info("GET请求参数："+config.url().substring(idx+1));
                }
            }
            //执行请求操作，并拿到结果（同步阻塞）
            resp = (config.context() == null) ? config.client().execute(request) : config.client().execute(request, config.context());

            if (config.isReturnRespHeaders()) { //可以添加一个返回值header，返回值header添加到cookie
                //获取所有response的header信息 //覆盖原有的header
                config.headers(resp.getAllHeaders());
                //打印header
                //for (int i = 0; i < resp.getAllHeaders().length; i++) {
                //    System.out.println(resp.getAllHeaders()[i].getName() + " : " + resp.getAllHeaders()[i].getValue());
                //}
            }
            /*保持登录，如果是单线程可以使用CookieStore cookieStore =new BasicCookieStore();添加到HttpClient
            HttpClient httpClient= HttpClientCustom.custom()
                    .pool(1000, 800).setDefaultCookieStore(new BasicCookieStore())
                    .setDefaultRequestConfig(defaultRequestConfig).build();
            多线程要使用context,只维护一份httpClient
                    */
            /*//返回的cookie添加到HttpClientContext，登录时传入HttpClientContext对象即可，登录自动保存session
            if (config.context() == null) {
                Header[] headers = resp.getAllHeaders();
                for (int i = 0; i < headers.length; i++) {
                    System.out.println(headers[i].getName() + " : " + headers[i].getValue());
                    if ("JSESSIONID".equals(headers[i].getName())) {
                        String[] JSESSIONIDs = headers[i].getValue().split(";")[0].split("=");
                        BasicClientCookie cookie = new BasicClientCookie(JSESSIONIDs[0], JSESSIONIDs[1]); //JSESSIONID
                        cookie.setVersion(0);
                        try {
                            cookie.setDomain(new URL(config.url()).getHost());//设置范围
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cookie.setPath("/");
                        CookieStore cookieStore = new BasicCookieStore();
                        cookieStore.addCookie(cookie);
                        HttpClientContext context = new HttpClientContext();
                        context.setCookieStore(cookieStore);
                    }
                }
            }*/
            //获取结果实体
            //System.out.println(resp);
            return resp;

        } catch (IOException e) {
            throw new HttpProcessException(e);
        }
    }

    //-----------华----丽----分----割----线--------------
    //-----------华----丽----分----割----线--------------
    //-----------华----丽----分----割----线--------------

    /**
     * 转化为字符串
     *
     * @param resp     响应对象
     * @param encoding 编码
     * @return 返回处理结果
     * @throws HttpProcessException http处理异常
     */
    private static String fmt2String(HttpResponse resp, String encoding) {
        String body = "";
        try {
            if (resp.getEntity() != null) {
                // 按指定编码转换结果实体为String类型
                body = EntityUtils.toString(resp.getEntity(), encoding);
                //pers.dafacloud.utils.httpclientUtil.common.Utils.info(body);
            } else {//有可能是head请求
                body = resp.getStatusLine().toString();
            }
            EntityUtils.consume(resp.getEntity());
        } catch (Exception e) {
            //throw new HttpProcessException(e);
            e.printStackTrace();
        } finally {
            close(resp);
        }
        return body;
    }

    /**
     * 根据请求方法名，获取request对象
     *
     * @param url    资源地址
     * @param method 请求方式
     * @return 返回Http处理request基类
     */
    private static HttpRequestBase getRequest(String url, HttpMethods method) {
        HttpRequestBase request = null;
        switch (method.getCode()) {
            case 0:// HttpGet
                request = new HttpGet(url);
                break;
            case 1:// HttpPost
                request = new HttpPost(url);
                break;
            case 2:// HttpHead
                request = new HttpHead(url);
                break;
            case 3:// HttpPut
                request = new HttpPut(url);
                break;
            case 4:// HttpDelete
                request = new HttpDelete(url);
                break;
            case 5:// HttpTrace
                request = new HttpTrace(url);
                break;
            case 6:// HttpPatch
                request = new HttpPatch(url);
                break;
            case 7:// HttpOptions
                request = new HttpOptions(url);
                break;
            default:
                request = new HttpPost(url);
                break;
        }
        return request;
    }

    /**
     * 尝试关闭response
     *
     * @param resp HttpResponse对象
     */
    private static void close(HttpResponse resp) {
        try {
            if (resp == null) return;
            //如果CloseableHttpResponse 是resp的父类，则支持关闭
            if (CloseableHttpResponse.class.isAssignableFrom(resp.getClass())) {
                ((CloseableHttpResponse) resp).close();
            }
        } catch (IOException e) {
            //pers.dafacloud.utils.httpclientUtil.common.Utils.exception(e);
            System.out.println("关闭链接失败～");
        }
    }

}
