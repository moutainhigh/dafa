package pers.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.Header;
import pers.dafacloud.constant.LotteryConstant;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;
import pers.utils.urlUtils.UrlBuilder;

/**
 * https://dafacloud.atlassian.net/browse/PJ-2548  【彩票系统】彩票CMS编辑用户信息页面优化
 */
public class TestQueryUsersInfoForAudit {

    private static String host = "http://pt04.dafacloud-test.com";
    //private static String host = "http://pt02.dafacloud-test.com";

    private static String queryUsersInfoForAudit = host + "/v1/users/queryUsersInfoForAudit";
    ///v1/users/getUserInfoForTransaction

    public static void main(String[] args) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .build();
        HttpCookies httpCookies = HttpCookies
                .custom()
                .setBasicClientCookie(host, "JSESSIONID", "8ACA46307A74B0D581BEE671459F12DC");

        String fullUrl = UrlBuilder
                .custom()
                .url(queryUsersInfoForAudit)
                .addBuilder("registerIp", "20.189.72.180")
                .addBuilder("lastLoginIp", "")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "10")
                .addBuilder("userId", "50395151")
                .fullUrl();
        System.out.println(fullUrl);
        String result = DafaRequest.get(HttpConfig.custom().url(fullUrl).headers(headers).context(httpCookies.getContext()));
        System.out.println(JsonFormat.formatPrint(result));
    }
}
