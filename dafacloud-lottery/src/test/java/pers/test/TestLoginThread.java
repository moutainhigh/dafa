package pers.test;

import com.alibaba.fastjson.JSONObject;
import pers.dafacloud.dafaLottery.Login;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.listUtils.ListRemoveRepeat;
import pers.utils.randomNameAddrIP.RandomIP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestLoginThread {

    //private static String host = "http://52.76.195.164:8010";
    private static String host = "http://52.77.207.64:8010";
    private static String loginUrl = host + "/v1/users/login";
    static List<String> list = Collections.synchronizedList(new ArrayList<>());
    private static ExecutorService execute = Executors.newFixedThreadPool(300);

    /**
     *
     */
    public static void main(String[] args) {
        List<String> users = FileUtil.readFile("/Users/duke/Documents/dafaUsers/dev2Dafa.txt").subList(0, 1999);
        for (int j = 0; j < 10000; j++) {
            for (int i = 0; i < users.size(); i++) {
                String[] userArray = users.get(i).split(",");
                login(userArray[0], userArray[1]);
            }

        }
    }

    public static void function01(List<String> users) {
        CountDownLatch cdl = new CountDownLatch(1500);
        for (int i = 0; i < 1; i++) {
            int finalI = i;
            execute.execute(() -> {
                login0(users.get(finalI).split(","));
                cdl.countDown();
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            cdl.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        execute.shutdown();
        System.out.println(list.size());
        //System.out.println(list);
        System.out.println(ListRemoveRepeat.removeRepeat(list).size());
    }

    public static void login0(String[] users) {
        login(users[0], users[1]);
    }

    public static void login(String userId, String username) {
        String ip = RandomIP.getRandomIp();
        HttpHeader httpHeader = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                //.other("x-forwarded-for", ip)
                //.other("x-remote-IP", ip)
                //.other("X-Real-IP", ip);
                .other("x-tenant-code", "dafa")
                .other("x-source-Id", "3")
                .other("x-user-id", userId)
                .other("x-user-name", username)
                .other("x-client-ip", ip)
                .other("x-user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-url", "caishen02.com");
        String body = Login.getLoginBody(username, "123qwe");
        HttpCookies httpCookies = HttpCookies.custom();
        HttpConfig httpConfig = HttpConfig.custom().url(loginUrl).body(body).headers(httpHeader.build()).context(httpCookies.getContext());
        for (int i = 0; i < 1; i++) {
            String result = DafaRequest.post(httpConfig);
            System.out.println(result);
            try {
                list.add(JSONObject.parseObject(result).getString("data"));
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(result);
                System.out.println(e.getMessage());
            }

        }
    }
}
