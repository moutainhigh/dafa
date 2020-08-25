package pers.test;

import com.alibaba.fastjson.JSONObject;
import pers.dafacloud.dafaLottery.Login;
import pers.utils.Md5HA1.AESCrossDomainUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.listUtils.ListSplit;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试最后操作时间
 */
public class TestLastOperationTime {
    private static ExecutorService executors = Executors.newFixedThreadPool(1000);
    private static String host = "http://caishen02.com";
    //private static String host = "http://dafacloud-test.com";

    public static void main(String[] args) {
        getTokenIpTask();
        //operationTask();
        //getSessionTask();
    }


    /**
     * 使用登录返回的x-token，来做其他操作
     */
    public static void getSessionTask() {
        List<String> tokens = FileUtil.readFile(TestLastOperationTime.class.getResourceAsStream("/test/token.txt"));//.subList(0,2);
        System.out.println(tokens.size());
        List<List<String>> tokensList = ListSplit.split(tokens, 300);
        CountDownLatch cdl = new CountDownLatch(tokensList.size());
        for (int i = 0; i < tokensList.size(); i++) {
            int finalI = i;
            executors.execute(() -> getSession(tokensList.get(finalI), cdl));
        }
        try {
            cdl.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getSession(List<String> tokens, CountDownLatch cdl) {
        //@RequestParam("ip")String ip,@RequestParam(Constant.JSESSIONID)String jsessionId,@RequestParam("url")String url,@RequestParam("api")String api
        //String ip = RandomIP.getRandomIp();
        HttpConfig httpConfig = HttpConfig.custom();
        for (int i = 0; i < tokens.size(); i++) {
            try {
                String JSESSIONID = AESCrossDomainUtil.decrypt(tokens.get(i)).replace("_dafatoken", "");
                //System.out.println(JSESSIONID);
                String url = UrlBuilder.custom()
                        //.url("http://192.168.254.100:8010/v1/users/getSession")
                        .url("http://52.76.195.164:8010/v1/users/getSession")
                        //.url("http://192.168.254.100:8010/v1/users/getSession")
                        .addBuilder("ip", "13.250.0.161")
                        .addBuilder("JSESSIONID", JSESSIONID)
                        .addBuilder("url", "caishen02.com")
                        .addBuilder("api", "users")
                        .fullUrl();
                for (int j = 0; j < 1; j++) {
                    System.out.println(DafaRequest.get(httpConfig.url(url)));
                }
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println("异常：" + e.getMessage());
            }
        }
        cdl.countDown();


    }


    /**
     * 使用登录返回的x-token，来做其他操作
     */
    public static void operationTask() {
        List<String> tokens = FileUtil.readFile(TestLastOperationTime.class.getResourceAsStream("/test/token.txt"));
        System.out.println(tokens.size());
        List<List<String>> tokensList = ListSplit.split(tokens, 1000);
        CountDownLatch cdl = new CountDownLatch(tokensList.size());
        for (int i = 0; i < tokensList.size(); i++) {
            int finalI = i;
            executors.execute(() -> operation(tokensList.get(finalI), cdl));
        }
        try {
            cdl.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用登录返回的x-token，来做其他操作
     */
    public static void operation(List<String> tokens, CountDownLatch cdl) {
        String ip = RandomIP.getRandomIp();
        HttpConfig httpConfig = HttpConfig.custom();
        for (int i = 0; i < tokens.size(); i++) {
            HttpHeader httpHeader0 = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .other("x-forwarded-for", ip)
                    .other("x-remote-IP", ip)
                    .other("X-Real-IP", ip)
                    .other("X-Token", tokens.get(i));
            httpConfig.headers(httpHeader0.build());
            System.out.println(DafaRequest.get(httpConfig.url(host + "/v1/balance/queryBalanceFront")));
            //try {
            //    Thread.sleep(200);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
        cdl.countDown();
    }

    /**
     * 先登录获取x-token
     */
    public static void getTokenIpTask() {
        List<String> users = FileUtil.readFile(TestLastOperationTime.class.getResourceAsStream("/test/test01.txt")).subList(1000, 2000);
        System.out.println(users.size());
        List<List<String>> usersList = ListSplit.split(users, 1000);
        CountDownLatch cdl = new CountDownLatch(usersList.size());
        for (int i = 0; i < usersList.size(); i++) {
            int finalI = i;
            executors.execute(() -> getTokenIp(usersList.get(finalI), cdl));
        }
        try {
            cdl.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getTokenIp(List<String> users, CountDownLatch cdl) {
        for (int i = 0; i < users.size(); i++) {
            String[] userA = users.get(i).split(",");
            String ip = RandomIP.getRandomIp();
            HttpHeader httpHeader = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .other("x-forwarded-for", ip)
                    .other("x-remote-IP", ip)
                    .other("X-Real-IP", ip)
                    .other("X-Real-IP", ip)
                    .other("x-user-id", userA[0])
                    .other("x-user-name", userA[1])
                    .other("x-tenant-code", "dafa")
                    .other("x-user-agent", "1")
                    .other("x-url", "0.0.0.0:8082")
                    .other("x-source-id", "1")
                    .other("x-domain", "http://0.0.0.0:8082")
                    .other("x-token", "96db9951ccef4f66ab869ed70f55e0f0")
                    .other("x-client-ip", ip);
            String body = Login.getLoginBody(userA[1], "123qwe");
            HttpCookies httpCookies = HttpCookies.custom();
            //手动设置cookie
            //52.77.207.64
            //httpCookies.getCookieStore().addCookie(DafaCloudLogin.productCookie("63550DAE2429C93A798049352B63AD1C",host));
            //52.76.195.164 52.77.207.64
            HttpConfig httpConfig = HttpConfig.custom().url("http://52.76.195.164:8010/v1/users/login").body(body).headers(httpHeader.build()).context(httpCookies.getContext());
            String result = DafaRequest.post(httpConfig);
            //System.out.println(userA[0] + " - " + result);
            JSONObject resultObj = JSONObject.parseObject(result);
            JSONObject dataObj = resultObj.getJSONObject("data");
            if (result == null || !result.contains("成功")) {
                System.out.println(userA[0] + "," + result);
                throw new RuntimeException("登录失败");
            }
            if (dataObj != null) {
                String token = dataObj.getString("token");
                System.out.println(token);
            }
        }
        cdl.countDown();
    }
}
