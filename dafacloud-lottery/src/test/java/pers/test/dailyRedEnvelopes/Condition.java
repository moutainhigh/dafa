package pers.test.dailyRedEnvelopes;

import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;

public class Condition {

    //private static String[] user = {"test", "50000298", "duke011"};
    private static String[] user = {"dafa", "50000511", "dafai0001"};
    //private static String[] user = {"dafa", "50000512", "dafai0002"};
    //private static String[] user = {"dafa", "60960920", "czxz0001"};
    private static Header[] headers = HttpHeader.custom()
            //.contentType("application/x-www-form-urlencoded;charset=UTF-8")
            //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .other("x-tenant-code", user[0])
            //.other("x-source-Id", "3")
            .other("x-user-id", user[1])
            .other("x-user-name", user[2])
            //.other("x-client-ip", "118.143.214.129")
            .other("x-is-test", "1")
            //.other("x-url", "dafacloud.com")
            .build();
    private static String host = "http://52.76.195.164";

    private static HttpConfig httpConfig = HttpConfig.custom()
            .headers(headers);


    /**
     *
     */
    @Test(description = "累计充值，累计投注（彩票和棋牌）")
    public static void userHistory() {
        String url = host + ":8060/v1/report/userReport/userHistory/180?startTime=2020-10-01&endTime=2020-10-22&filter=";
        httpConfig.url(url);
        String result = DafaRequest.get(httpConfig);
        System.out.println(JsonFormat.formatPrint(result));

    }

}
