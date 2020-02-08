package dafagame.test;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

/**
 * cms创建账号
 * */
public class CreateUserFromCms {
    private static String host = "http://pt.dafagame-pro.com";
    private static String addAccount = host + "/v1/users/addAccount";


    public static void main(String[] args) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .build();
        HttpConfig httpConfig = HttpConfig
                .custom()
                .headers(headers)
                .context(HttpCookies.custom()
                        .setBasicClientCookie(host, "JSESSIONID", "BE421F0CCEEEB55612E60763A6B7B1CB")
                        .getContext()
                );
        String body = UrlBuilder
                .custom()
                .addBuilder("nickname")
                .addBuilder("password", "45f00148904b46829b2cdd1d30209348")
                .addBuilder("safetyPassword", "6075c6d7f8068626000aab51f66ac591")
                .addBuilder("amount", "99999")
                .addBuilder("userType", "0")
                .fullBody();

        for (int i = 0; i < 200; i++) {
            String result = DafaRequest.post(httpConfig.url(addAccount).body(body));
            System.out.println(result);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
