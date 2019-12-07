package dafagame.register;

import org.apache.http.Header;
import pers.utils.Md5HA1.MD5Util;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

import java.util.Base64;

public class Login {

    public static void main(String[] args) throws Exception{
        login("13012340010");
        //System.out.println(MD5Util.shaEncode("duke123"));
        //System.out.println(getLoginBody("0.19427303010137842"));
    }


    public static void login(String phone) {
        String url = "http://dukecocosrelease.dafagame-test.com";
        //String url = "http://10.169.68.78:7010";//坚果本地
        //String url = "http://192.168.30.17:7010";//内网地址
        //String phone = "13012345682";//手机号
        String inviteCode = "0960743";//邀请码
        String ip = RandomIP.getRandomIp();
        //注册接口
        try {
            //System.out.println(code);
            //String password = DigestUtils.md5Hex("duke123");//MD5加密一次

            Header[] headers0 =
                    HttpHeader
                            .custom()
                            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                            //.other("x-forwarded-for", ip)
                            //.other("x-remote-IP", ip)
                            //.other("X-Real-IP", ip)
                            //.other("x-tenant-code", "duke")
                            //.other("x-tenant-type", "1")
                            //.other("x-client-ip", ip)
                            //.other("x-source-id", "1")
                            //.other("x-client-version","123")
                            //.other("x-url","dukecocosrelease.dafagame-test.com")
                            .build();
            //URLEncoder.encode(password,"utf-8")
            String random = String.valueOf(Math.random());
            String body0 = UrlBuilder.custom()
                    .addBuilder("inviteCode")
                    .addBuilder("userType", "1")
                    .addBuilder("accountNumber", phone)
                    .addBuilder("password", getLoginBody(random))
                    .addBuilder("random", Base64.getEncoder().encodeToString(random.getBytes()))
                    .fullBody();
            System.out.println(body0);
            HttpConfig httpConfig = HttpConfig.custom()
                    .url(url + "/v1/users/login")
                    .body(body0) //URLEncoder.encode(body0,"utf-8")
                    .headers(headers0);
            //String result0 = DafaRequest.post(url + "/v1/users/register?", body0, headers);
            String result0 = DafaRequest.post(httpConfig);
            System.out.println("result:" + result0);
        } catch (Exception e) {
            System.out.println(phone + "==============登录失败:");
        }
    }

    public static String getLoginBody(String random) {
        String password = "";
        try {
            password =
                    MD5Util.getMd5(MD5Util.getMd5(MD5Util.getMd5("duke123") + MD5Util.shaEncode("duke123")) + random);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }


}
