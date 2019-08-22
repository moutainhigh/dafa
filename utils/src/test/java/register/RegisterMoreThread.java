package register;

import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.urlUtils.UrlBuilder;

public class RegisterMoreThread {

    public static void main(String[] args) {
        String password = DigestUtils.md5Hex("duke123");

        String url = "http://alysiacocosrelease.dafagame-test.com/v1/users/register";
        String phone = "1311234";
        String inviteCode = "9862148";

//        String url ="http://cindy.dafagame-test.com";
//        String phone = "1311234";
//        String inviteCode = "1457502";

        for (int i = 0; i < 5; i++) {
            int index = i;
            new Thread(() -> {
                int n = 40;
                for (int j = 1600+index * n; j < 1600+index * n + n; j++) {
                    String body = UrlBuilder.custom()
                            .addBuilder("password", password)
                            .addBuilder("confirmPassword", password)
                            .addBuilder("phone", String.format("%s%s", phone, String.format("%04d", j)))
                            .addBuilder("code", "")//手机验证码
                            .addBuilder("inviteCode", inviteCode)
                            .fullBody();
                    System.out.println(body);
                    HttpConfig httpConfig = HttpConfig
                            .custom()
                            .url(url)
                            .body(body);
                    String result = DafaRequest.post(httpConfig);
                    System.out.println(result);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Test(description = "测试")
    public static void test01() {
        String url = "http://alysiacocosrelease.dafagame-test.com/v1/users/register";
        String password = DigestUtils.md5Hex("duke123");
        String phone = "1311234";
        for (int i = 0; i < 500; i++) {
            String body = UrlBuilder.custom()
                    .addBuilder("password", password)
                    .addBuilder("confirmPassword", password)
                    .addBuilder("phone", String.format("%s%s", phone, String.format("%04d", i)))
                    .addBuilder("code", "")//手机验证码
                    .addBuilder("inviteCode", "1457502")
                    .fullBody();
            System.out.println(body);
            HttpConfig httpConfig = HttpConfig
                    .custom()
                    .url(url)
                    .body(body);
            String result = DafaRequest.post(httpConfig);
            System.out.println(result);
        }
    }

    @Test(description = "测试")
    public static void test03() {
        System.out.println(360%60);
    }



}