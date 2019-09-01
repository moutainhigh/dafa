package pers.testShaLv;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterUserByIP {

    private static String register = "http://52.76.195.164:8010/v1/users/register";

    //多线程只能在main方法中运行
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(16);
        List<String> users = new ArrayList<>();
        //users.add("shalv100,33033033");
        //users.add("shalv101,57442158");
        //users.add("shalv102,44392771");
        //users.add("shalv103,84290029");
        //users.add("shalv104,11236528");
        //users.add("shalv105,89361522");
        users.add("shalv106,57263934");
        for (int i = 0; i < users.size(); i++) {
            String[] userArray = users.get(i).split(",");
            executor.execute(()->{
                for (int j = 100; j < 500; j++) {
                    register(String.format("%sa%04d", userArray[0], j), userArray[1], userArray[0]);

                }
            });
        }
    }

    private static void register(String username, String inviteCode, String tenantCode) {
        String password = DigestUtils.md5Hex(username + DigestUtils.md5Hex("duke123"));
        String body = UrlBuilder.custom()
                .addBuilder("inviteCode", inviteCode)
                .addBuilder("userName", username)
                .addBuilder("password", password)
                .fullBody();
        System.out.println(body);
        Header[] headers = HttpHeader
                .custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .other("x-tenant-code", tenantCode)
                .other("x-user-name", username)
                .other("x-source-Id", "1")
                .other("x-user-id", "")
                .other("x-client-ip", RandomIP.getRandomIp())
                .other("x-url", "")
                .build();
        String result = DafaRequest.post(register, body, headers);
        System.out.println(result);
    }
}
