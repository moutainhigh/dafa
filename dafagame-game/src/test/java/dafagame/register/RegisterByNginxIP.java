package dafagame.register;

import dafagame.AESUtils;
import dafagame.MD5Util;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URLEncoder;

public class RegisterByNginxIP {
    public static void main(String[] args) {

        String phone = "1301234";
        for (int i = 0; i < 1000; i++) { //13012340104
            try {
                registerDafaGame(String.format("%s%04d", phone, i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //棋牌游戏账号，随机ip
    public static void registerDafaGame(String phone) {
        String url = "http://dukecocosrelease.dafagame-test.com";
        //String url = "http://192.168.30.17:7010";//内网地址
        //String phone = "13012345682";//手机号
        String inviteCode = "0960743";//邀请码
        String ip = RandomIP.getRandomIp();
        Header[] headers = HttpHeader
                .custom()
                .other("x-forwarded-for", ip)
                .other("x-remote-IP", ip)
                .other("X-Real-IP", ip)
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                //.other("x-tenant-code", "duke")
                //.other("x-tenant-type", "1")
                //.other("x-client-ip", ip)
                //.other("x-source-id", "1")
                .build();
        //获取短信验证码接口
        String body = UrlBuilder.custom()
                .addBuilder("phone", phone)
                .addBuilder("phoneCodeType", "2")
                .fullBody();
        String result = DafaRequest.post(url + "/v1/users/sendPhoneCode", body, headers);
        JSONObject jsonResult = JSONObject.fromObject(result);
        //AssertUtil.assertCode(jsonResult.getInt("code") == 1, phone + "," + result);
        if(jsonResult.getInt("code") != 1){
            //System.out.println(phone + "," + result);
            System.err.println(phone + "," + result);
            return;
        }
        System.out.println(result);
        String code = jsonResult.getString("data");//短信验证码
        //String code = "6323";
        //注册接口
        try {
            //System.out.println(code);
            //String password = DigestUtils.md5Hex("duke123");//MD5加密一次
            String password = AESUtils.AESEncode(MD5Util.getEncryptionInformation("duke123"), MD5Util.getMd5(code).getBytes());
            Header[] headers0 =
                    HttpHeader
                            .custom()
                            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                            .other("x-forwarded-for", ip)
                            .other("x-remote-IP", ip)
                            .other("X-Real-IP", ip)
                            //.other("x-tenant-code", "duke")
                            //.other("x-tenant-type", "1")
                            //.other("x-client-ip", ip)
                            //.other("x-source-id", "1")
                            .build();

            String body0 = UrlBuilder.custom()
                    .addBuilder("password", URLEncoder.encode(password,"utf-8"))
                    .addBuilder("confirmPassword", URLEncoder.encode(password,"utf-8"))
                    .addBuilder("phone", phone)
                    .addBuilder("code", code)
                    .addBuilder("inviteCode", inviteCode)
                    .fullBody();
            System.out.println(body0);
            HttpConfig httpConfig = HttpConfig.custom()
                    .url(url + "/v1/users/register")
                    .body(body0) //URLEncoder.encode(body0,"utf-8")
                    .headers(headers0);
            //String result0 = DafaRequest.post(url + "/v1/users/register?", body0, headers);
            String result0 = DafaRequest.post(httpConfig);
            System.out.println("result:" + result0);
        } catch (Exception e) {
            System.out.println(phone + "==============注册失败:");
        }
    }
}
