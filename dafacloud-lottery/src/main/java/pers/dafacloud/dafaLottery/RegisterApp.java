package pers.dafacloud.dafaLottery;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.listUtils.ListSplit;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterApp {

    //private static String register = "http://caishen02.com/v1/users/register";
    //private static String register = "http://52.76.195.164:8010/v1/users/register";//第一套
    private static String register = "https://2019rsnewdatacloudapp.dafacloudapp.com/v1/users/register";//APP
    //private static String register = "http://52.77.207.64:8010/v1/users/register";//第二套
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);
    private static String[] inviteCodes = {"69662317", "72562999", "35996704", "03961612", "97291515", "16557652"};

    //多线程只能在main方法中运行
    public static void main(String[] args) {
        register("dukeabcd05");
    }

    /**
     * 注册任务
     */
    private static void register(String username) {
        String password = DigestUtils.md5Hex(username + DigestUtils.md5Hex("123qwe"));
        String body = UrlBuilder.custom()
                //.addBuilder("inviteCode", inviteCodes[(int) (Math.random() * inviteCodes.length)])
                .addBuilder("inviteCode", "03704085")
                .addBuilder("userName", username)
                .addBuilder("password", password)
                .fullBody();
        for (int i = 0; i < 1; i++) {
            //String ip = RandomIP.getRandomIp();
            String ip = "127.9.9.2";
            Header[] headers = HttpHeader
                    .custom()
                    .other("x-forwarded-for", ip)
                    .other("x-remote-IP", ip)
                    .other("X-Real-IP", ip)
                    .other("x-remote-ip", ip)
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    //.other("x-tenant-code", "dafa")
                    //.other("x-source-Id", "3")
                    //.other("x-client-ip", ip)
                    //.other("x-user-id", "51321300")
                    //.other("x-user-name", "duke01")
                    .build();
            HttpConfig httpConfig = HttpConfig.custom().body(body).headers(headers).url(register);
            String result = DafaRequest.post(httpConfig);
            System.out.println(result);
            if (JSONObject.parseObject(result).getInteger("code") != 1) {
                System.out.println("重试" + i + "次 -【" + username + "】" + result);
                if (result.contains("已被注册")) {
                    break;
                }
            } else {
                break;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
