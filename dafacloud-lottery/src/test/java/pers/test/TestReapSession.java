package pers.test;

import org.apache.http.Header;
import pers.dafacloud.dafaLottery.ConstantLottery;
import pers.dafacloud.dafaLottery.Login;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;

import java.util.List;

public class TestReapSession {

    private static String host = "http://caishen01.com:8080";
    private static String loginUrl = host + "/v1/users/login";

    /**
     * session 重复的问题
     */

    public static void main(String[] args) throws Exception {
        List<String> list = FileUtil.readFile(TestReapSession.class.getResourceAsStream("/userPro.txt"));
        String ip = RandomIP.getRandomIp();
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-forwarded-for", ip)
                .other("x-remote-IP", ip)
                .other("X-Real-IP", ip)
                .build();
        for (int i = 0; i < list.size(); i++) {
            String body = Login.getLoginBody(list.get(i), "123456");
            HttpCookies httpCookies = HttpCookies.custom();
            HttpConfig httpConfig = HttpConfig.custom().url(loginUrl).body(body).headers(headers).context(httpCookies.getContext());
            String result = DafaRequest.post(httpConfig);
            System.out.println(result);
            Thread.sleep(3000);
        }
    }

}
