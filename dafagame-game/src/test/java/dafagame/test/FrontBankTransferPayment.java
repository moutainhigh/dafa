package dafagame.test;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

/**
 * pro 响应时间测试
 */
public class FrontBankTransferPayment {
    private static String host = "http://120.76.132.211";
    private static String frontAppPaySort = host + "/v1/transaction/frontBankTransferPayment";

    public static void main(String[] args) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Source-Id", "2")
                .other("Tenant-Code", "demo")
                .other("Session-Id", "867bd58d43c94d72a77d3194aee5931d")
                .build();

        HttpConfig httpConfig = HttpConfig
                .custom()
                .headers(headers);
        handle(httpConfig);
    }

    /**
     * 测试pro接口响应时间，执行次数
     */
    private static void handle(HttpConfig httpConfig) {
        for (int i = 0; i < 100; i++) {
            task(httpConfig);
        }
    }

    /**
     * 测试pro接口响应时间
     */
    private static void task(HttpConfig httpConfig) {
        System.out.println(DafaRequest.get(httpConfig.url(frontAppPaySort).body("")));
    }
}
