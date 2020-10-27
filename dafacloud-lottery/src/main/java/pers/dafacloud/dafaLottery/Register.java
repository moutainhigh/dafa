package pers.dafacloud.dafaLottery;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.listUtils.ListSplit;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Register {

    //private static String register = "http://caishen02.com/v1/users/register";
    //private static String register = "http://52.76.195.164:8010/v1/users/register";//第一套
    private static String register = "http://52.77.207.64:8010/v1/users/register";//第二套
    private static ExecutorService executors = Executors.newFixedThreadPool(300);
    private static String[] inviteCodes = {"69662317", "72562999", "35996704", "03961612", "97291515", "16557652"};

    //多线程只能在main方法中运行
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        //List<String> list = new ArrayList<>(Arrays.asList("dev2td0409".split(",")));
        for (int i = 250; i < 500; i++) {
            //    //list.add(String.format("adafa%05d", i));
            //    list.add(String.format("dev2td%04d", i));
            //list.add(String.format("dev1tdf%05d", i));
            list.add(String.format("dev2dfa%04d", i));
        }
        //System.out.println(list);
        schedule(list);
        // register("adafa00001");
        //registerTask(list);

    }

    /**
     * 多线程执行
     *
     * @param list 注册用户list,拆分成多个子list多线程执行
     */
    static void schedule(List<String> list) {
        List<List<String>> list0 = ListSplit.split(list, 1000);
        CountDownLatch cdl = new CountDownLatch(list0.size());
        for (int i = 0; i < list0.size(); i++) {
            List<String> sub = list0.get(i);
            executors.execute(() -> registerTask(sub, cdl));
        }
        try {
            cdl.await();
            executors.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param list 注册用户list
     */
    private static void registerTask(List<String> list, CountDownLatch cdl) {
        for (String username : list) {
            try {
                register(username);
                //Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cdl.countDown();
    }

    /**
     * 注册任务
     */
    private static void register(String username) {
        String password = DigestUtils.md5Hex(username + DigestUtils.md5Hex("123qwe"));
        String body = UrlBuilder.custom()
                //.addBuilder("inviteCode", inviteCodes[(int) (Math.random() * inviteCodes.length)])
                //.addBuilder("inviteCode", "40924811")
                .addBuilder("inviteCode", "12928954")
                .addBuilder("userName", username)
                .addBuilder("password", password)
                .fullBody();
        for (int i = 0; i < 1; i++) {
            String ip = RandomIP.getRandomIp();
            //String ip = "127.9.9.2";
            Header[] headers = HttpHeader
                    .custom()
                    .other("x-forwarded-for", ip)
                    .other("x-remote-IP", ip)
                    .other("X-Real-IP", ip)
                    .other("x-remote-ip", ip)
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .other("x-tenant-code", "dafa")
                    .other("x-source-Id", "3")
                    .other("x-client-ip", ip)
                    //.other("x-user-id", "51321300")
                    //.other("x-user-name", "duke01")
                    .build();
            HttpConfig httpConfig = HttpConfig.custom().body(body).headers(headers).url(register);
            String result = DafaRequest.post(httpConfig);
            System.out.println(result);
            if (JSONObject.parseObject(result).getInteger("code") != 1) {
                if (result.contains("已被注册")) {
                    break;
                }
                System.out.println("重试" + i + "次 -【" + username + "】" + result);
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

    @Test(description = "测试")
    public static void test01() {
        String username = String.format("auto%05d", (int) (Math.random() * 100000));
        System.out.println(username);
        System.out.println( );
    }
}
