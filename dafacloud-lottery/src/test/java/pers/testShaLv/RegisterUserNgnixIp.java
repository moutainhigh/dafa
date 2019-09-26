package pers.testShaLv;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterUserNgnixIp {

    private static String register = "http://caishen01.com/v1/users/register";

    //多线程只能在main方法中运行
    public static void main(String[] args) {
        for (int j = 7; j < 8; j++) {
            String username = String.format("%sa%03d", "duke", j);
            try {
                register(username, "17793516");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void register(String username, String inviteCode) {
        String password = DigestUtils.md5Hex(username + DigestUtils.md5Hex("duke123"));
        String body = UrlBuilder.custom()
                .addBuilder("inviteCode", inviteCode)
                .addBuilder("userName", username)
                .addBuilder("password", password)
                .fullBody();
        System.out.println(body);
        String ip = RandomIP.getRandomIp();
        Header[] headers = HttpHeader
                .custom()
                .other("x-forwarded-for", ip)
                .other("x-remote-IP", ip)
                .other("X-Real-IP", ip)
                .other("x-remote-ip", ip)
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .build();
        HttpConfig httpConfig = HttpConfig.custom().body(body).headers(headers).url(register);
        String result = DafaRequest.post(httpConfig);
        System.out.println(result);
    }


    @Test(description = "测试")
    public static void test01() {

    }

}
