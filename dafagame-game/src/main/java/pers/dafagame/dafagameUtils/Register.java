package pers.dafagame.dafagameUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import pers.utils.Md5HA1.AESUtils;
import pers.utils.Md5HA1.MD5Util;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URLEncoder;

public class Register {
    private static String host = "http://120.25.177.43";
    private static String register = Constants.host + "/v1/users/register";

    /**
     * 获取注册请求body
     */
    public static void register(HttpConfig httpConfig, String phone, String password) throws Exception {
        String code = PhoneCode.sendPhoneCode(httpConfig, phone);
        String passwordCode = getPasswordCode(password, code);
        if (StringUtils.isEmpty(passwordCode))
            return;
        String body = UrlBuilder.custom()
                .addBuilder("password", URLEncoder.encode(passwordCode, "utf-8"))
                .addBuilder("confirmPassword", URLEncoder.encode(passwordCode, "utf-8"))
                .addBuilder("phone", phone)
                .addBuilder("code", code)
                .addBuilder("inviteCode", "6090013")
                .fullBody();
        String result = DafaRequest.post(httpConfig.body(body).url(register));
        System.out.println(phone+"---"+result);
    }

    /**
     * 注册请求的加密密码 [MD5(MD5(密码) + SHA1(密码))] ，AES秘钥 = MD5(验证码)
     */
    public static String getPasswordCode(String password, String code) throws Exception {
        return AESUtils.AESEncode(MD5Util.getEncryptionInformation(password), MD5Util.getMd5(code).getBytes());

    }

    public static void registerLoop(HttpConfig httpConfig, String phone) throws Exception {
        String ip = RandomIP.getRandomIp();
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-forwarded-for", ip)
                .other("x-remote-IP", ip)
                .other("X-Real-IP", ip)
                .other("Tenant-Code","xiaoqiu")
                .other("Source-Id","1")
                .build();
        httpConfig.headers(headers);
        register(httpConfig, phone, "123qwe");
    }

    public static void main(String[] args) throws Exception {
        HttpConfig httpConfig = HttpConfig.custom();
        //for (int i = 530; i < 531; i++) {
        //    registerLoop(httpConfig, String.format("1801234%04d", i));
        //}
        //for (int i = 400; i < 500; i++) {
        //    registerLoop(httpConfig, String.format("1801234%04d", i));
        //}
        //for (int i = 0; i < 100; i++) {
        //    registerLoop(httpConfig, String.format("1801234%04d", i));
        //}
        registerLoop(httpConfig, "13112345678");
    }

}
