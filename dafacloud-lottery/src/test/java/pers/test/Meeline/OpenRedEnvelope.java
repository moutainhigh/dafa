package pers.test.Meeline;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OpenRedEnvelope {
    //private static String host = "http://caishen02.com";
    private static String host = "http://47.244.250.106:8176";
    private static ExecutorService executors = Executors.newFixedThreadPool(500);

    public static void open(String meeToken) {
        String openRedEnvelope = host + "/api/v1/transfer/openRedEnvelope";
        Header[] httpHeader = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("pAuthorization", meeToken)
                .other("random", "9311h87181ba")
                .build();

        String snatchRedEnvelope = host + "/api/v1/transfer/snatchRedEnvelope";

        String recordId = "HB560488695111606066";

        String body0 = UrlBuilder.custom()
                .addBuilder("recordId", recordId)
                .addBuilder("r", "7281h577856e")
                .addBuilder("log", "{\"m\":\"2\",\"js_init_time\":\"1593763935778\"}")
                .fullBody();
        String result0 = DafaRequest.post(HttpConfig.custom().url(snatchRedEnvelope).body(body0).headers(httpHeader));
        System.out.println(result0);

        String body = UrlBuilder.custom()
                .addBuilder("recordId", recordId)
                .addBuilder("muid", "0000e91a") //0005a73a
                .addBuilder("meelineNickname", "煌账饲徘开") //发红包的人
                .addBuilder("r", "7281h5026daa")
                .addBuilder("log", "{\"m\":\"2\",\"js_init_time\":\"" + System.currentTimeMillis() + "\"}")
                .fullBody();
        String result = DafaRequest.post(HttpConfig.custom().url(openRedEnvelope).body(body).headers(httpHeader));
        System.out.println(result);
    }

    public static void task(List<String> meeTokens) {
        for (String meeToken : meeTokens) {
            executors.execute(() -> open(meeToken));
        }
    }

    public static void main(String[] args) {
        open("8a8c5040ff95891f5b0373ea6b224ada24d51fbddf6fe060d2ea55bef5c23e5c");
        //List<String> meeTokens = FileUtil.readFile("/Users/duke/Documents/dafaUsers/meeToken.txt");
        //task(meeTokens);
    }


}
