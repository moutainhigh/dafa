package pers.test.Wheels;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;

/**
 * 前台获取配置
 * */
public class GetActivatedPrizeWheelsConfig {
    private static final String HOST = "http://52.76.195.164:8080";
    //private static final String HOST = "http://caishen02.com";
    private static final String getActivatedPrizeWheelsConfig = HOST + "/v1/activity/prizeWheels/getActivatedPrizeWheelsConfig?";


    private static void getConfig(String userName, String userId) {
        Header[] headers = HttpHeader.custom()
                //.contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("Referer", "http://caishen02.com/activity/631")
                .other("x-tenant-code", "dafa")
                .other("x-source-Id", "3")
                .other("x-user-name", userName)
                .other("x-user-id", userId)
                .other("x-client-ip", "118.143.214.129")
                .other("x-is-test", "0")
                .other("x-url", "caishen02.com")
                //.other("X-Token", "88kP/yRmYDe7CyRRUvgcizFzBJevxkhRU8UBsMYCzeEEvULQrx5JvyRLylbSeSBp")
                .build();

        HttpConfig httpConfig = HttpConfig.custom()
                .url(getActivatedPrizeWheelsConfig)
                .headers(headers);
        for (int i = 0; i < 1; i++) {
            String result = DafaRequest.get(httpConfig);
            //System.out.println(userName + " - " + result);
            System.out.println(JsonFormat.formatPrint(result));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        getConfig("dafai0001","50000511");
    }
}
