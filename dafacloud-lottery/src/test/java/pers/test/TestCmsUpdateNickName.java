package pers.test;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;
import pers.utils.randomNameAddrIP.RandomName;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;

/**
 * https://dafacloud.atlassian.net/browse/PJ-2548  【彩票系统】彩票CMS编辑用户信息页面优化
 */
public class TestCmsUpdateNickName {

    private static String host = "http://pt05.dafacloud-test.com";
    //private static String host = "http://pt02.dafacloud-test.com";

    private static String manageUpdateUserNickName = host + "/v1/users/manageUpdateUserNickName";
    ///v1/users/getUserInfoForTransaction

    public static void main(String[] args) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .build();
        HttpCookies httpCookies = HttpCookies
                .custom()
                .setBasicClientCookie(host, "JSESSIONID", "D7408153BEAA6C139058604528FB13F5");

        List<String> userIds = FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/userId.txt");
        HttpConfig httpConfig = HttpConfig.custom()
                .url(manageUpdateUserNickName)
                .headers(headers)
                .context(httpCookies.getContext());
        for (int i = 0; i < userIds.size(); i++) {
            String body = UrlBuilder
                    .custom()
                    .addBuilder("userId", userIds.get(i))
                    .addBuilder("nickName", RandomName.getRandomJianHan(5))
                    .fullBody();
            String result = DafaRequest.post(httpConfig.body(body));
            System.out.println(result);
        }
    }
}
