package pers.dafacloud.dafaLottery;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Login {

    private static String host = ConstantLottery.host;
    private static String loginUrl = host + "/v1/users/login";

    /**
     * 登录
     * 返回带cookie的httpConfig
     */
    public static HttpConfig loginReturnHttpConfig(String username) {
        String ip = RandomIP.getRandomIp();
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-forwarded-for", ip)
                .other("x-remote-IP", ip)
                .other("X-Real-IP", ip)
                .build();
        String body = getLoginBody(username, "123456");
        HttpCookies httpCookies = HttpCookies.custom();
        HttpConfig httpConfig = HttpConfig.custom().url(loginUrl).body(body).headers(headers).context(httpCookies.getContext());
        String result = DafaRequest.post(httpConfig);
        System.out.println(result);
        return httpConfig;
    }

    /**
     * 批量登录
     * 返回List<HttpConfig>
     */
    public static List<HttpConfig> loginLoop(List<String> users) {
        List<HttpConfig> list = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            list.add(loginReturnHttpConfig(users.get(i)));
        }
        return list;
    }

    /**
     * 获取 加密后的loginBody
     * 账号需要转小写
     */
    public static String getLoginBody(String userName, String password) {
        //随机码
        String random = "dafacloud_" + Math.random();
        //md5加密后的密码
        String passwordCode = DigestUtils.md5Hex(DigestUtils.md5Hex(userName + DigestUtils.md5Hex(password)) + random);
        //随机
        String encode = Base64.getEncoder().encodeToString(random.getBytes());
        String body = String.format("userName=%s&password=%s&random=%s", userName, passwordCode, encode);
        return body;
    }

}
