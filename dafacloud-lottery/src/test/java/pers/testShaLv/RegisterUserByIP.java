package pers.testShaLv;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;
import pers.utils.fileUtils.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterUserByIP {
    private static String register = "http://52.76.195.164:8010/v1/users/register";

    //@Test(description = "多个站点通过ip注册账号")
    //public static void test01() {
    //    String username = "sv001a0001";
    //    String tenantCode = "shalv001";
    //    String inviteCode = "54249031";
    //
    //}

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(16);
        List<String> users = new ArrayList<>();
        users.add("shalv005,89201116");
        users.add("shalv006,48283488");
        //users.add("shalv007,74888642");
        for (int i = 0; i < users.size(); i++) {
            String[] userArray = users.get(i).split(",");
            int ii = i;
            //new Thread(() -> {
            //    //String[] userArrayInner = userArray;
            //    for (int j = 0; j < 100; j++) {
            //        register(String.format("%sa%04d", userArray[0], j), userArray[1], userArray[0]);
            //        //if(ii==0||ii==1){
            //        //    System.out.println(String.format("%sa%04d", userArray[0], j));
            //        //}
            //    }
            //}).run();
            executor.execute(()->{
                //String[] userArrayInner = userArray;
                for (int j = 4000; j < 6000; j++) {
                    register(String.format("%sa%04d", userArray[0], j), userArray[1], userArray[0]);
                    //if(ii==0||ii==1){
                    //    System.out.println(String.format("%sa%04d", userArray[0], j));
                    //}
                }
            });
            //try {
            //    Thread.sleep(100);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
    }

    public static void function01() {
        ExecutorService executor = Executors.newFixedThreadPool(16);
        List<String> users = FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/shalvUses.txt");
        for (int i = 0; i < users.size(); i++) {
            String[] userArray = users.get(i).split(",");
            int ii = i;
            //new Thread(() -> {
            //    //String[] userArrayInner = userArray;
            //    for (int j = 0; j < 100; j++) {
            //        register(String.format("%sa%04d", userArray[0], j), userArray[1], userArray[0]);
            //        //if(ii==0||ii==1){
            //        //    System.out.println(String.format("%sa%04d", userArray[0], j));
            //        //}
            //    }
            //}).run();
            executor.execute(()->{
                //String[] userArrayInner = userArray;
                for (int j = 0; j < 100; j++) {
                    register(String.format("%sa%04d", userArray[0], j), userArray[1], userArray[0]);
                    //if(ii==0||ii==1){
                    //    System.out.println(String.format("%sa%04d", userArray[0], j));
                    //}
                }
            });
            //try {
            //    Thread.sleep(100);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
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
