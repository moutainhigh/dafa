package pers.testProductBug;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;

import java.util.Base64;

public class LoginGXAPPbyIP {

    /**
     * 【区域限制】共享app登录会多出一条登录日志和会员日志
     */
    public static void main(String[] args) {
        //String url = "http://52.76.195.164:8010/v1/users/login";

        //String url = "http://3.1.152.170:8010/v1/users/login";
        String url = "http://caishen02.com/v1/users/login";
        Header[] headers = HttpHeader
                .custom()
                .other("x-tenant-code", "dafa")
                .other("x-source-Id", "3")
                //.other("x-user-id", "50000512")
                //.other("x-user-name", "dafai0002")
                .other("x-user-id", "51321300")
                .other("x-user-name", "duke01")
                .other("x-client-ip", "118.143.214.129")
                .other("x-url", "dafacloud.com")
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .build();
        String result = DafaRequest.post(url, getLoginBody("duke01", "123456"), headers);
        System.out.println(result);
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
        //System.out.println(encode);
        String body = String.format("userName=%s&password=%s&random=%s", userName, passwordCode, encode);
        //System.out.println(body);
        return body;
    }

}
