package dafagame.test;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

public class TestGetRepayment {
    static String getGameDomain = "http://klqyhumdtz.com/v1/security/getGameDomain";
    static String host = "http://120.76.132.211";//ws://20.189.79.208:7250
    static String frontAppPaySort = host + "/v1/transaction/frontBankTransferPayment";

    public static void main(String[] args)  {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                //.other("Origin", "")
                .other("Source-Id", "2")
                .other("Tenant-Code", "demo")
                .other("Session-Id", "867bd58d43c94d72a77d3194aee5931d")
                .build();

        HttpConfig httpConfig = HttpConfig
                .custom()
                .headers(headers);

        //安全服务返回gameDomaindomain
        String gameDomain = DafaRequest.get(httpConfig.url(getGameDomain));
        System.out.println(gameDomain);

        //http://127.0.0.1:52143/v1/game/getTenantGameService?gameCode=201

    }

    /**
     * 测试pro接口响应时间，执行次数
     */
    public static void handle(HttpConfig httpConfig) {
        for (int i = 0; i < 100; i++) {
            task(httpConfig);
        }
    }

    /**
     * 测试pro接口响应时间
     */
    public static void task(HttpConfig httpConfig) {
        System.out.println(DafaRequest.get(httpConfig.url(frontAppPaySort).body("")));
    }
}
