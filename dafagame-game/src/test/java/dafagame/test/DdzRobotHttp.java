package dafagame.test;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

public class DdzRobotHttp {

    private static String host = "http://a4cdf4aef23dc11ea8f02061e82846b5-26ec5fa4ac0099ca.elb.ap-east-1.amazonaws.com";
        private static String customizeTestTable = host + "/v1/game-ddz-http/customizeTestTable";

    public static void main(String[] args) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Source-Id", "2")
                .build();
        HttpConfig httpConfig = HttpConfig
                .custom()
                .headers(headers);
        String result = DafaRequest.post(httpConfig.url(customizeTestTable));
        System.out.println(result);
    }
}
