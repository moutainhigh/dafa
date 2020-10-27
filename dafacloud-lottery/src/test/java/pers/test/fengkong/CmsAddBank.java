package pers.test.fengkong;

import cn.binarywang.tools.generator.BankCardNumberGenerator;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CmsAddBank {
    private final static String host = "http://pt01.dafacloud-test.com";
    private final static String updateBankCardCms = host + "/v1/users/updateBankCardCms";
    private static ExecutorService executors = Executors.newFixedThreadPool(300);

    private static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .build();
    private static HttpConfig httpConfig = HttpConfig.custom()
            .url(updateBankCardCms)
            .context(HttpCookies.custom()
                    .setBasicClientCookie(host, "JSESSIONID", "8B62BB304A124B52EFDD3794345688F6")
                    .getContext())
            .headers(headers);

    public static void main(String[] args) {
        //List<String> userList = FileUtil.readFile("/Users/duke/Documents/dafaUsers/dev1dafa.txt");
        List<String> userList = FileUtil.readFile("/Users/duke/Documents/apache-jmeter-5.0/bin/dev1Dafa02.txt");
        for (int i = 0; i < userList.size(); i++) {
            String[] s = userList.get(i).split(",");
            //executors.execute(() -> addBank(s[0], s[1]));
            addBank(s[0], s[1]);
        }
    }

    /**
     *
     */
    public static void addBank(String userid, String username) {
        String bankCard = BankCardNumberGenerator.getInstance().generate();
        System.out.println(bankCard);
        String body = UrlBuilder
                .custom()
                .addBuilder("userName", username)
                .addBuilder("userId", userid)
                .addBuilder("bankName", "建设银行")
                .addBuilder("province", "北京市")
                .addBuilder("city", "北京市")
                .addBuilder("bankCardNumber", bankCard)
                .addBuilder("isDisable", "false")
                .addBuilder("accountName", "杜克")
                .addBuilder("isOtherBank", "false")
                .fullBody();
        httpConfig.body(body);
        String result = DafaRequest.post(httpConfig);
        System.out.println(username + " - " + result);

    }
}
