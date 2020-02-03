package pers.test;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

import java.net.URL;

public class TestSessionLoginPro {

    private static Logger log = LoggerFactory.getLogger(TestSessionLoginPro.class);

    private static String host = "https://m.flc112.com/";

    private static String userInfo = host + "/v1/users/info";


    /**
     * session 问题，通过session 登录
     */
    public static void main(String[] args) throws Exception {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                //.other("Origin", "http://2019.dafacloud-admin.com")
                //.other("x-manager-name", "dafa")
                ////.other("x-manager-id","23456")
                //.other("x-tenant-code", "caisw")
                .build();
        CookieStore cookieStore = new BasicCookieStore();
        //ck123886
        BasicClientCookie basicClientCookie = new BasicClientCookie("JSESSIONID", "4591F0849373446CE35F14C1F825EE66");
        basicClientCookie.setDomain(new URL(host).getHost());
        basicClientCookie.setPath("/");
        cookieStore.addCookie(basicClientCookie);
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);
        HttpConfig httpConfig = HttpConfig.custom().headers(headers).context(context);
        String usernameInit = "flcabcd";
        for (int i = 0; i < 10000000; i++) {
            String reslut = DafaRequest.get(httpConfig.url(userInfo));
            String username = JSONObject.fromObject(reslut).getJSONObject("data").getString("userName");
            log.info(username);
            if (!usernameInit.equals(username)) {
                System.out.println(reslut);
                break;
            }

            Thread.sleep(2000 * 2);
        }

    }
}
