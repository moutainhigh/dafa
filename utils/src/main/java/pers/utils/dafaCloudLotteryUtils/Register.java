package pers.utils.dafaCloudLotteryUtils;

import org.apache.commons.codec.digest.DigestUtils;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class Register {

    //private static  final  String url = "http://caishen02.com/v1/users/register";

    private static final String url = "http://caishen03.com/v1/users/register"; //pre http://caishen03.com/v1/users/register

    public static void main(String[] args) {
        //String username = "autodf";
        //String inviteCode = "97291515";

        String username = "autodf";
        String inviteCode = "72562999";//测试环境大发站，正式账号邀请码
        for (int i = 3; i < 500; i++) {
            try {
                registerMeth(url, username + String.format("%05d", i), inviteCode);
            } catch (Exception e) {
                System.out.println("==============注册失败:" + username + String.format("%05d", i));
                //e.printStackTrace();
            }
        }
        //registerMeth("dafadfa"+String.format("%05d", 0));
        //String password = DigestUtils.md5Hex("dafai0002" + DigestUtils.md5Hex("123456"));
        //System.out.println(password);
    }

    public static void registerMeth(String host, String userName, String inviteCode) {
        String password = DigestUtils.md5Hex(userName + DigestUtils.md5Hex("123456"));
        //String body = "inviteCode="+inviteCode+"&userName="+userName+"&password="+password;
        String body = UrlBuilder.custom()
                .addBuilder("inviteCode", inviteCode)
                .addBuilder("userName", userName)
                .addBuilder("password", password)
                .fullBody();
        /*
        Header[] headers = HttpHeader.custom()
                .other("x-tenant-code", "dafa")
                .other("x-source-id", "2")
                .other("x-client-ip", "192.168.1.1")
                .build();
                */
        //System.out.println(body);
        String result = DafaRequest.post(String.format("%s%s", host, "/v1/users/register"), body);
        System.out.println(userName + "：" + result);
        if (!result.contains("注册成功")) {
            System.out.println("=============注册失败" + result);
        }
    }
}
