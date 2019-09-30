package register;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

public class RegisterMoreThreadCP {

    public static void main(String[] args) {
        String url = "http://52.77.207.64:8010/v1/users/register";
        String username0 = "dukedua";
        String inviteCode = "72562999";

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1; j++) {
                    int index = (int) (Math.random() * 1000000);
                    String username = String.format("%s%s", username0 , index);//String.format("%04d", index)
                    String password = DigestUtils.md5Hex(username + DigestUtils.md5Hex("123456"));
                    String body = UrlBuilder.custom()
                            .addBuilder("inviteCode", inviteCode)
                            .addBuilder("userName", username)
                            .addBuilder("password", password)
                            .fullBody();
                    System.out.println(body);
                    Header[] headers = HttpHeader.custom()
                            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                            .other("x-client-ip", RandomIP.getRandomIp())
                            .other("x-source-id", "1")
                            .other("x-tenant-code", "dafa")
                            .build();
                    HttpConfig httpConfig = HttpConfig
                            .custom()
                            .url(url)
                            .body(body).headers(headers);

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
}