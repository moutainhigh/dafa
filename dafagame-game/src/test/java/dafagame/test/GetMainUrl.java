package dafagame.test;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

/**
 * 登录页修复功能，获取官网地址
 */
public class GetMainUrl {

    private static String getMainUrl = "http://52.139.157.97/v1/management/tenant/getMainUrl";

    public static void main(String[] args) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Source-Id", "2")
                .other("Tenant-Code", "demo")
                .other("Session-Id", "867bd58d43c94d72a77d3194aee5931d")
                .build();
        HttpConfig httpConfig = HttpConfig
                .custom()
                .headers(headers);
        String gameDomain = DafaRequest.get(httpConfig.url(getMainUrl));
        System.out.println(gameDomain);
    }
}
