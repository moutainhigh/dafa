package pers.dafacloud.testHttpClientUtil;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import pers.dafacloud.utils.httpclientUtil.HttpClientUtil;
import pers.dafacloud.utils.httpclientUtil.builder.HCB;
import pers.dafacloud.utils.httpclientUtil.common.HttpConfig;
import pers.dafacloud.utils.httpclientUtil.common.HttpHeader;

public class TestHttpPool02 {
    private static final Header[] headers = HttpHeader.custom()
            .userAgent("Mozilla/5.0")
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .build();

    public  static void test01() throws  Exception{

        HttpClient client= HCB.custom().pool(100, 20).timeout(10000).build();
        final HttpConfig cfg = HttpConfig.custom().client(client).headers(headers).url("http://m.caishen02.com");
        HttpClientUtil.post(cfg);

        System.out.println(client);
//        System.out.println(client2);


    }

    public static void main(String[] args) throws Exception {

        test01();

    }
}
