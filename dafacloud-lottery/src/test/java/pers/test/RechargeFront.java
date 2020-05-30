package pers.test;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*充值压测*/
public class RechargeFront {
    private static String host = "http://52.77.207.64:8030";
    private static String rechargeFrontPaymentRecord = host + "/v1/transaction/rechargeFrontPaymentRecord";
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    public static void main(String[] args) {
        List<String> usersList = FileUtil.readFile(RechargeFront.class.getResourceAsStream("/users/dev1DafaIP.txt"));
        for (int i = 0; i < 500; i++) {
            String[] userArray = usersList.get(i).split(",");
            Header[] headers = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .other("x-user-name", userArray[0])
                    .other("x-user-id", userArray[1])
                    .other("x-tenant-code", "dafa")
                    .other("x-source-Id", "1")
                    .other("x-is-test", "false")
                    //.other("x-client-ip", RandomIP.getRandomIp())
                    .build();
            String body = UrlBuilder.custom()
                    .addBuilder("id", "1252")
                    .addBuilder("amount", "100")
                    .addBuilder("paymentType", "11")
                    .addBuilder("remark", "1")
                    .fullBody();
            HttpConfig httpConfig = HttpConfig.custom()
                    .url(rechargeFrontPaymentRecord)
                    .body(body)
                    .headers(headers);
            excutors.execute(() -> RechargeFront.task(httpConfig));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void task(HttpConfig httpConfig) {
        //String body = UrlBuilder.custom()
        //        .addBuilder("id", "1252")
        //        .addBuilder("amount", "100")
        //        .addBuilder("paymentType", "11")
        //        .addBuilder("remark", "1")
        //        .fullBody();
        for (; ; ) {
            //int amount = (int) (Math.random() * 99);
            String result = DafaRequest.post(httpConfig);
            System.out.println(result);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
