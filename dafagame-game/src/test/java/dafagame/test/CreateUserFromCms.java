package dafagame.test;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomName;
import pers.utils.urlUtils.UrlBuilder;

/**
 * cms创建账号
 */
public class CreateUserFromCms {
    //private static String host = "http://pt.dafagame-pro.com";
    //private static final String HOST = "https://dg2020.dafagame-admin.com";
    private static final String HOST = "https://pt2.dafagame-test.com";
    private static final String addAccount = HOST + "/v1/users/addAccount";


    public static void main(String[] args) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .build();
        HttpConfig httpConfig = HttpConfig
                .custom()
                .headers(headers)
                .context(HttpCookies.custom()
                        .setBasicClientCookie(HOST, "JSESSIONID", "31D74DA176D09F65910792D211799CF5")
                        .getContext()
                );

        for (int i = 0; i < 51; i++) {
            String body = UrlBuilder
                    .custom()
                    .addBuilder("nickname", "杜克" + RandomName.getRandomJianHan(3))
                    //.addBuilder("password", "45f00148904b46829b2cdd1d30209348")
                    //.addBuilder("safetyPassword", "6075c6d7f8068626000aab51f66ac591")
                    .addBuilder("password", "45f00148904b46829b2cdd1d30209348") //a1c4b171da15a6361242c77db89f4056/duke123 ;45f00148904b46829b2cdd1d30209348/123qwe
                    .addBuilder("safetyPassword", "6075c6d7f8068626000aab51f66ac591") //100200
                    .addBuilder("amount", "100000")
                    .addBuilder("userType", "0")
                    .fullBody();
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
