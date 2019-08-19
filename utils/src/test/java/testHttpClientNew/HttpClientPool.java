package testHttpClientNew;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
//import pers.dafacloud.utils.httpclientUtils.HttpConfig;
//import pers.dafacloud.utils.httpclientUtils.HttpHeader;
//import pers.dafacloud.utils.httpclientUtils.Request;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.httpclientUtils.Request;

/**
 * 多线程测试
 */
public class HttpClientPool {


    public static void main(String[] args) throws Exception {
//        String url = "https://github.com/Arronlong/httpclientutil";
        String url = "http://m.caishen02.com/v1/users/login";
        final String[] body = {
                "userName=dafai0001&password=c11093dbb5e46b5faa0374f309f117e9&random=ZGFmYWNsb3VkXzAuNTM0Nzc1Mzg3MTQ4MjQzNw%3D%3D",
                "userName=dafai0002&password=37f15c612efe918c41256e9ce7a7fbdf&random=ZGFmYWNsb3VkXzAuODI5OTA4MjkxMjQ1NjczNA%3D%3D"
        };
        Header[] headers = HttpHeader
                .custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .build();

        for (int i = 0; i < body.length; i++) {
            int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //定义cookie存储
                        HttpClientContext context = new HttpClientContext();
                        CookieStore cookieStore = new BasicCookieStore();
                        context.setCookieStore(cookieStore);
                        //
                        HttpConfig httpConfig = HttpConfig
                                .custom()
                                .headers(headers) //返回header true会覆盖原来的header,因为返回的是json
                                .url(url)
                                .body(body[index])
                                .context(context);

                        System.out.println("index:" + index + "," + Request.post(httpConfig)); //执行登陆请求
                        for (Cookie cookie : context.getCookieStore().getCookies()) {
                            // System.out.println(cookie.getName()+"--"+cookie.getValue());
                        }
                        //httpConfig.context();

                        for (Header header : httpConfig.headers()) {
                            //System.out.println(header.getName()+","+header.getValue());
                        }
                        //System.out.println(context);
                        //System.out.println("index:"+index+","+Request.get("http://m.caishen02.com/v1/users/info?",context));
                        httpConfig.url("http://m.caishen02.com/v1/users/info?");//修改请求地址
                        //System.out.println(httpConfig.url());
                        System.out.println("index:" + index + "," + Request.get(httpConfig));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
