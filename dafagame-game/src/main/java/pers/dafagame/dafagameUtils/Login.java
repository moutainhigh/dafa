package pers.dafagame.dafagameUtils;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import pers.utils.Md5HA1.MD5Util;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

import java.util.Base64;

public class Login {
    private static String host = Constants.host;
    private static String loginUrl = host + "/v1/users/login";

    public static void main(String[] args) {
        //String random = "9722";
        //getPassword(random, "123qwe");

    }

    /**
     * cocos前台加密
     * 入参：random base64加密数据
     */
    public static String getPassword(String random, String password) {
        String passwordEncode = "";
        try {
            passwordEncode =
                    MD5Util.getMd5(MD5Util.getMd5(MD5Util.getMd5(password) + MD5Util.shaEncode(password)) + random);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passwordEncode;
    }


    public static HttpConfig Login(String accountNumber, String password) {
        String ip = RandomIP.getRandomIp();
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-forwarded-for", ip)
                .other("x-remote-IP", ip)
                .other("X-Real-IP", ip)
                .build();
        String random = "9722";
        String body = UrlBuilder.custom()
                .addBuilder("accountNumber", accountNumber)
                .addBuilder("password", getPassword(random, password))
                .addBuilder("random", Base64.getEncoder().encodeToString(random.getBytes()))
                .addBuilder("userType", "1")
                .addBuilder("inviteCode")
                .fullBody();
        HttpConfig httpConfig = HttpConfig
                .custom()
                .url(loginUrl)
                .headers(headers)
                .body(body)
                .context(HttpCookies.custom().getContext());
        String result = DafaRequest.post(httpConfig);
        System.out.println(result);
        String sessionId = JSONObject.fromObject(result).getJSONObject("data").getString("sessionId");
        Header[] headers0 = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-forwarded-for", ip)
                .other("x-remote-IP", ip)
                .other("X-Real-IP", ip)
                .other("Session-Id", sessionId)
                .build();
        httpConfig.headers(headers0);
        return httpConfig;

    }
}
