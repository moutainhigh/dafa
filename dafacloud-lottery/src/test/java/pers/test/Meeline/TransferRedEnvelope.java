package pers.test.Meeline;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

public class TransferRedEnvelope {
    //private static String host = "http://caishen02.com";
    private static String host = "http://52.76.195.164:8120";
    private static String snatchRedEnvelope = host + "/v1/transfer/snatchRedEnvelope";
    private static String openRedEnvelope = host + "/v1/transfer/openRedEnvelope";

    //抢红包
    public static void snatchRedEnvelope(String users) {
        String[] userArray = users.split(",");
        Header[] httpHeader = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                //.other("x-forwarded-for", ip)
                //.other("x-remote-IP", ip)
                //.other("X-Real-IP", ip)
                .other("x-tenant-code", "dafa")
                .other("x-source-Id", "3")
                .other("x-user-id", userArray[0])
                .other("x-user-name", userArray[1])
                .other("x-client-ip", RandomIP.getRandomIp())
                .other("x-source-Id", "1")
                .other("x-is-test", "0")
                .other("x-url", "caishen02.com").build();
        String result = DafaRequest.post(HttpConfig.custom().body("recordId=HB300097825116997465").url(snatchRedEnvelope).headers(httpHeader));
        System.out.println(result);
        String body = UrlBuilder.custom()
                .addBuilder("recordId","HB300097825116997465")
                //.addBuilder("muid","0003b927")
                //.addBuilder("meelineNickname","煌账饲徘开")
                .fullBody();
        String result0 = DafaRequest.post(HttpConfig.custom().body(body).url(openRedEnvelope).headers(httpHeader));
        System.out.println(result0);
    }

    public static void main(String[] args) {
        snatchRedEnvelope("50001180,dafai0670");
    }


}
