package pers.dafacloud.dafaLottery;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.listUtils.ListSplit;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Register {

    private static String register = "http://caishen03.com/v1/users/register";
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    //多线程只能在main方法中运行
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 20101; i < 20121; i++) {
            list.add(String.format("adafa%05d", i));
        }
        System.out.println(list);
        //schedule(list);
       // register("adafa00001");


        registerTask(list);

    }

    static void schedule(List<String> list) {
        List<List<String>> list0 = ListSplit.split(list, 1000);
        for (int i = 0; i < 10; i++) {
            List<String> sub = list0.get(i);
            excutors.execute(() -> registerTask(sub));
        }
    }


    private static void registerTask(List<String> list) {
        for (String username : list) {
            try {
                register(username);
                //Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void register(String username) {
        String password = DigestUtils.md5Hex(username + DigestUtils.md5Hex("123qwe"));
        String body = UrlBuilder.custom()
                .addBuilder("inviteCode", "58889589")
                .addBuilder("userName", username)
                .addBuilder("password", password)
                .fullBody();
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
}
