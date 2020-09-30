package pers.test.speedUrl;

import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;

//流量报表 - 获取加速域名，匹配
public class GetAllTenantUrl {


    @Test(description = "测试")
    public static void test01() {
        final String host = "http://2019.dafacloud-admin.com";
        String url = host + "/v1/management/tenant/getAllTenantUrl";

        String result = DafaRequest.get(HttpConfig.custom().url(url).context(HttpCookies
                .custom()
                .setBasicClientCookie(host, "JSESSIONID", "6584A7303487E4151203173A27619F81")
                .getContext()));
        System.out.println(result);
    }

    @Test(description = "测试")
    public static void test02() {
        final String host = "http://52.76.195.164:8040";
        String url = host + "/v1/management/tenant/getAllTenantUrl";

        Header[] headers = HttpHeader.custom()
                .other("x-tenant-code", "dafa")
                .other("x-manager-name", "duke")
                .other("x-manager-id", "100035")
                .other("x-is-system", "1")
                .other("x-client-ip", "118.143.214.129").build();
        String result = DafaRequest.get(HttpConfig.custom().url(url).headers(headers));
        System.out.println(JsonFormat.formatPrint(result));
    }
}
