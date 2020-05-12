package pers.utils.dafaRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.httpclientUtils.Request;
import pers.utils.logUtils.Log;
import pers.utils.propertiesUtils.PropertiesUtil;

import java.net.URL;

public class DafaRequest {

    private static HttpConfig httpConfig = HttpConfig.custom();
    private static HttpClientContext context = new HttpClientContext();
    private static CookieStore cookieStore = new BasicCookieStore();
    private static BasicClientCookie cookie;
    private static Header[] headers;
    private static String cookieJSESSIONID = PropertiesUtil.getProperty("cookieJSESSIONID");
    private static String headerSessionId = PropertiesUtil.getProperty("headerSessionId");
    /**
     * 棋牌系统
     * http://pt.dafagame-test.com:83  后台
     * http://duke.dafagame-test.com:86 前台duke站
     * http://dafagame-test.com 测试站
     * http://cocodev.dafagame-test.com
     * http://cindy.dafagame-test.com
     * http://pt.dafagame-test.com
     */
    //默认值
    private static String host = PropertiesUtil.getProperty("host");//"http://cindy.dafagame-test.com";
    private static String hostCoCos = PropertiesUtil.getProperty("hostCoCos");//0
    private static String hostCms = PropertiesUtil.getProperty("hostCms");//1


    static {
        //初始化含cookie的context，
        cookie = new BasicClientCookie("JSESSIONID", cookieJSESSIONID); //JSESSIONID
        cookie.setVersion(0);
        try {
            if (StringUtils.isNotEmpty(host))
                cookie.setDomain(new URL(host).getHost());//设置范围
        } catch (Exception e) {
            e.printStackTrace();
        }
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        context.setCookieStore(cookieStore);
        //初始化请求头
        headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Session-Id", headerSessionId) //棋牌系统前台使用
                //.other("Tenant-Code","cindy")
                .build();
        httpConfig.headers(headers);
    }

    /**
     * get===============================================================================================================
     * 默认的httpConfig，默认的cookie，默认的header
     *
     * @param url
     */
    public static String get(String url) {
        String result;
        try {
            if (url.contains("http")) {
                result = Request.get(httpConfig.url(url.replace(" ", "%20")).context(context));//replaceAll 替换掉url中的空格
            } else {
                result = Request.get(httpConfig.url(host + url.replace(" ", "%20")).context(context));
            }
            //Log.info(String.format("get请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     *
     */
    public static String get(int env, String url) {
        String result;
        try {
            if (url.contains("http")) {
                result = Request.get(httpConfig.url(url.replace(" ", "%20")).context(context));//replaceAll 替换掉url中的空格

            } else {
                if (env == 0) {
                    cookie.setDomain(new URL(hostCoCos).getHost());//设置范围
                    result = Request.get(httpConfig.url(hostCoCos + url.replace(" ", "%20")).context(context));
                } else {
                    cookie.setDomain(new URL(hostCms).getHost());//设置范围
                    result = Request.get(httpConfig.url(hostCms + url.replace(" ", "%20")).context(context));
                }
            }
            //Log.info(String.format("get请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            Log.info("GET url解析错误" + hostCoCos);
            e.printStackTrace();
            return null;
        }
    }

    public static String get(String url, Header[] headers) {
        String result;
        httpConfig.headers(headers);
        try {
            if (url.contains("http")) {
                result = Request.get(httpConfig.url(url.replace(" ", "%20")).context(context));//replaceAll 替换掉url中的空格

            } else {
                result = Request.get(httpConfig.url(host + url.replace(" ", "%20")).context(context));
            }
            //Log.info(String.format("get请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            Log.info("GET url解析错误" + hostCoCos);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 默认的httpConfig,覆盖原有默认的cookie,默认的header
     *
     * @param url
     * @param cookies
     */
    public static String get(String url, String cookies) {
        cookie.setValue(cookies);
        String result;
        try {
            if (url.contains("http")) {
                cookie.setDomain(new URL(url).getHost());
                result = Request.get(httpConfig.url(url.replace(" ", "%20")).context(context));
            } else {
                result = Request.get(httpConfig.url(host + url.replace(" ", "%20")).context(context));
            }
            //Log.info(String.format("get请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get自定义httpConfig,如果有header，使用已有的header，没有则使用默认的header
     */
    public static String get(HttpConfig httpConfig) {
        String result;
        try {
            if (httpConfig.headers() == null)
                result = Request.get(httpConfig.headers(headers));
            else
                result = Request.get(httpConfig);
            //Log.info(String.format("get请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * post===============================================================================================================
     * 1.使用默认的HttpConfig，设置默认的context，默认的cookie
     */
    public static String post(String url, String body) {
        String result;
        try {
            if (url.contains("http")) {
                result = Request.post(httpConfig.url(url).body(body).context(context));
            } else {
                result = Request.post(httpConfig.url(host + url).body(body).context(context));
            }
            //Log.info(String.format("post请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String post(int env, String url, String body) {
        String result;
        try {
            if (url.contains("http")) {
                result = Request.post(httpConfig.url(url).body(body).context(context));
            } else {
                if (env == 0) {
                    cookie.setDomain(new URL(hostCoCos).getHost());//设置范围
                    result = Request.post(httpConfig.url(hostCoCos + url).body(body).context(context));
                } else {
                    cookie.setDomain(new URL(hostCms).getHost());//设置范围
                    result = Request.post(httpConfig.url(hostCms + url).body(body).context(context));
                }
            }
            //Log.info(String.format("post请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的HttpConfig，设置默认的context，默认的cookie，自定义的header
     */
    public static String post(String url, String body, Header[] httpHeader) {
        String result;
        try {
            if (url.contains("http")) {
                result = Request.post(httpConfig.url(url).body(body).context(context).headers(httpHeader));
            } else {
                result = Request.post(httpConfig.url(host + url).body(body).context(context).headers(httpHeader));
            }
            //Log.info(String.format("post请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 2.使用默认的HttpConfig，设置默认的context，自定义cookie的cookie
     *
     * @param cookieValue
     */
    public static String post(String url, String body, String cookieValue) {
        cookie.setValue(cookieValue);
        String result;
        try {
            if (url.contains("http")) {
                cookie.setDomain(new URL(url).getHost());
                result = Request.post(httpConfig.url(url).body(body).context(context).headers(headers));
            } else {
                result = Request.post(httpConfig.url(host + url).body(body).context(context));
            }
            //Log.info(String.format("post请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 自定义HttpConfig
     */
    public static String post(HttpConfig httpConfig) {
        String result;
        try {
            if (httpConfig.headers() == null)
                result = Request.post(httpConfig.headers(headers));
            else
                result = Request.post(httpConfig);
            //Log.info(String.format("post请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println(httpConfig.url());
            //System.out.println(httpConfig.body());
            return e.getMessage();
        }
    }

    /**
     * 上传
     */
    public static String upload(int env, String url, HttpEntity httpEntity, Header[] headers) {
        String result;
        try {
            if (url.contains("http")) {
                result = Request.post(httpConfig.url(url).context(context).headers(headers).httpEntity(httpEntity).headers(headers));

            } else {
                if (env == 0) {
                    cookie.setDomain(new URL(hostCoCos).getHost());//设置范围
                    result = Request.post(httpConfig.url(host + url).context(context).httpEntity(httpEntity).headers(headers));
                } else {
                    cookie.setDomain(new URL(hostCms).getHost());//设置范围
                    result = Request.post(httpConfig.url(hostCms + url).context(context).httpEntity(httpEntity).headers(headers));
                }
            }
            //Log.info(String.format("post请求结果返回:%s",result));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void down(HttpConfig httpConfig) {
        try {
            Request.down(httpConfig);
        } catch (Exception e) {
            Log.info("文件下载失败：" + e.getMessage());
            e.printStackTrace();
        }

    }
}
