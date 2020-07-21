package pers.test.Meeline;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

public class OpenRedEnvelope {
    private static String host = "http://caishen02.com";

    public static void open() {
        String openRedEnvelope = host + "/api/v1/transfer/openRedEnvelope";
        Header[] httpHeader = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("pAuthorization", "8644032ad287e80aab5225b029367e5a2c950fa0a6250eeb3132f3c44fb4536b")
                .other("random", "9311h87181ba")
                .build();

        String snatchRedEnvelope = host + "/api/v1/transfer/snatchRedEnvelope";
        String body0 = UrlBuilder.custom()
                .addBuilder("recordId","HB290252645111190032")
                .addBuilder("r","7281h577856e")
                .addBuilder("log","{\"m\":\"2\",\"js_init_time\":\"1593763935778\"}")
                .fullBody();
        String result0 = DafaRequest.post(HttpConfig.custom().url(snatchRedEnvelope).body(body0).headers(httpHeader));
        System.out.println(result0);

        String body = UrlBuilder.custom()
                .addBuilder("recordId", "HB290252645111190032")
                .addBuilder("muid", "0000e91a") //0005a73a
                .addBuilder("meelineNickname", "煌账饲徘开") //发红包的人
                .addBuilder("r", "7281h5026daa")
                .addBuilder("log", "{\"m\":\"2\",\"js_init_time\":\"" + System.currentTimeMillis() + "\"}")
                .fullBody();
        String result = DafaRequest.post(HttpConfig.custom().url(openRedEnvelope).body(body).headers(httpHeader));
        System.out.println(result);
    }

    public static void main(String[] args) {
        open();
    }


}
