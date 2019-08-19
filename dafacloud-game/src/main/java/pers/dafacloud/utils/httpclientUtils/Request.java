package pers.dafacloud.utils.httpclientUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import pers.dafacloud.utils.httpclientUtils.common.Utils;
import pers.dafacloud.utils.httpclientUtils.SSLClientCustom.SSLProtocolVersion;
import pers.dafacloud.utils.httpclientUtil.exception.HttpProcessException;


import java.io.IOException;

public class Request {

    //默认采用的http协议的HttpClient对象
    private static HttpClient client4HTTP;

    //默认采用的https协议的HttpClient对象
    private static HttpClient client4HTTPS;
    private static HttpConfig httpConfig  =  HttpConfig.custom();


    static {
        try {
            client4HTTP = HttpClientCustom.custom().build();
            client4HTTPS = HttpClientCustom.custom().sslpv(SSLProtocolVersion.TLSv1_2).ssl().build();
            httpConfig.headers(HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0")
                    .build());
        }catch (Exception e){
            System.out.println("创建https协议的HttpClient对象出错：{}");
        }
    }

    private static void create(HttpConfig config) {
        if(config.client()==null){//如果为空，设为默认client对象
            if(config.url().toLowerCase().startsWith("https://")){
                config.client(client4HTTPS);
            }else{
                config.client(client4HTTP);
            }
        }
    }
    //get 默认配置
    public static String get(String url) throws Exception{
        return get(httpConfig.url(url));
    }
    //传入cookie
    public static String get(String url, HttpContext httpContext) throws Exception{
        return get(httpConfig.url(url).context(httpContext));
    }

    /**
     * 以Get方式，请求资源或服务
     *
     * @param config		请求参数配置
     * @return	返回结果
     * @throws HttpProcessException	http处理异常
     */
    public static String get(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.GET));
    }

    /**
     * 以Post方式，请求资源或服务
     *
     //* @param client		client对象
     //* @param url			资源地址
     //* @param headers		请求头信息
     //* @param parasMap		请求参数
     //* @param context		http上下文，用于cookie操作
     //* @param encoding		编码
     * @return				返回处理结果
     * @throws HttpProcessException	http处理异常
     */
    /*public static String post(HttpClient client, String url, Header[] headers, HttpContext context) throws HttpProcessException {
        return post(HttpConfig.custom().client(client).url(url).headers(headers).context(context));
    }*/
    //使用使用默认header的post
    public static String post(String url,String body) throws Exception{
        return body+","+post(httpConfig.url(url).body(body));
    }
    //自定义header的psot
    public static String post(String url, String body,Header[] headers) throws Exception{
        return post(httpConfig.url(url).body(body).headers(headers));
    }

    /**
     * 以Post方式，请求资源或服务
     *
     * @param config		请求参数配置
     * @return				返回处理结果
     * @throws HttpProcessException	http处理异常
     */
    public static String post(HttpConfig config) throws HttpProcessException {
        return send(config.method(HttpMethods.POST));
    }

    /**
     * 请求资源或服务
     *
     * @param config		请求参数配置
     * @return				返回处理结果
     * @throws HttpProcessException	http处理异常
     */
    public static String send(HttpConfig config) throws HttpProcessException {
        return fmt2String(execute(config), config.outenc());
    }

    /**
     * 请求资源或服务
     *
     * @param config		请求参数配置
     * @return				返回HttpResponse对象
     * @throws HttpProcessException	http处理异常
     */
    private static HttpResponse execute(HttpConfig config) throws HttpProcessException {
        create(config);//获取链接 //根据链接创建client4HTTPS 或者 client4HTTP
        HttpResponse resp = null;
        try {
            //创建请求对象
            HttpRequestBase request = getRequest(config.url(), config.method()); //HttpGet HttpPost

            //设置超时
            request.setConfig(config.requestConfig());

            //设置header信息
            request.setHeaders(config.headers());

            //判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
            if(HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())){
                //List<NameValuePair> nvps = new ArrayList<NameValuePair>();

                if(request.getClass()==HttpGet.class) { //Get请求
                    //检测url中是否存在参数
                    //注：只有get请求，才自动截取url中的参数，post等其他方式，不再截取
                    //config.url(Utils.checkHasParas(config.url(), nvps, config.inenc()));
                }

                //装填参数
                //HttpEntity entity = Utils.map2HttpEntity(nvps, config.map(), config.inenc());
                HttpEntity entity = new StringEntity(config.body(), "UTF-8");

                //设置参数到请求对象中
                ((HttpEntityEnclosingRequestBase)request).setEntity(entity);

                /*Utils.info("请求地址："+config.url());
                if(nvps.size()>0){
                    Utils.info("请求参数："+nvps.toString());
                }
                if(config.json()!=null){
                    Utils.info("请求参数："+config.json());
                }*/
            }else{
                int idx = config.url().indexOf("?");
                Utils.info("请求地址："+config.url().substring(0, (idx>0 ? idx : config.url().length())));
                if(idx>0){
                    Utils.info("请求参数："+config.url().substring(idx+1));
                }
            }
            //执行请求操作，并拿到结果（同步阻塞）
            resp = (config.context()==null)?config.client().execute(request) : config.client().execute(request, config.context()) ;

            if(config.isReturnRespHeaders()){
                //获取所有response的header信息 //覆盖原有的header
                config.headers(resp.getAllHeaders());
            }

            //获取结果实体
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
     * @param resp			响应对象
     * @param encoding		编码
     * @return				返回处理结果
     * @throws HttpProcessException	http处理异常
     */
    private static String fmt2String(HttpResponse resp, String encoding) throws HttpProcessException {
        String body = "";
        try {
            if (resp.getEntity() != null) {
                // 按指定编码转换结果实体为String类型
                body = EntityUtils.toString(resp.getEntity(), encoding);
                //pers.dafacloud.utils.httpclientUtil.common.Utils.info(body);
            }else{//有可能是head请求
                body =resp.getStatusLine().toString();
            }
            EntityUtils.consume(resp.getEntity());
        } catch (IOException e) {
            throw new HttpProcessException(e);
        }finally{
            close(resp);
        }
        return body;
    }

    /**
     * 根据请求方法名，获取request对象
	 *
     * @param url			资源地址
	 * @param method		请求方式
	 * @return				返回Http处理request基类
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
     * @param resp				HttpResponse对象
     */
    private static void close(HttpResponse resp) {
        try {
            if(resp == null) return;
            //如果CloseableHttpResponse 是resp的父类，则支持关闭
            if(CloseableHttpResponse.class.isAssignableFrom(resp.getClass())){
                ((CloseableHttpResponse)resp).close();
            }
        } catch (IOException e) {
            //pers.dafacloud.utils.httpclientUtil.common.Utils.exception(e);
            System.out.println("关闭链接失败～");
        }
    }

}
