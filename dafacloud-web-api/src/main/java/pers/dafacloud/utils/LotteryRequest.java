package pers.dafacloud.utils;

import org.apache.http.Header;
import pers.dafacloud.constant.EvAttribute;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.httpclientUtils.Request;

/*
 EvAttribute.hostCms= "http://pt02.dafacloud-test.com";
 EvAttribute.jsessionid= "72BD073A8F2AD2D9FFF6A8FADB9FA88B";
 LotteryRequest.httpConfig = HttpConfig.custom()
 .context(HttpCookies
 .custom()
 .setBasicClientCookie(EvAttribute.hostCms, "JSESSIONID", EvAttribute.jsessionid)
 .getContext());
 */

public class LotteryRequest {

    public static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            //.other("X-Token", "")
            .build();

    public static HttpConfig httpConfig = HttpConfig.custom()
            .headers(headers)
            .context(HttpCookies
                    .custom()
                    .setBasicClientCookie(EvAttribute.hostCms, "JSESSIONID", EvAttribute.jsessionid)
                    .getContext());


    public static String getCms(String path) {
        try {
            return Request.get(httpConfig.url(EvAttribute.hostCms + path));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String postCms(String path, String body) {
        try {
            return Request.post(httpConfig.url(EvAttribute.hostCms + path).body(body));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
