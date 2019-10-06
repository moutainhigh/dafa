package dafagame.register;

import dafagame.AESUtils;
import dafagame.MD5Util;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

public class RegisterByNginxIP {
    public static void main(String[] args) {

        String phone = "1301234";
        for (int i = 3; i < 4; i++) {
            registerDafaGame(String.format("%s%04d", phone, i));
        }
    }

    //棋牌游戏账号，随机ip
    public static void registerDafaGame(String phone) {
        String url = "http://dukecocosrelease.dafagame-test.com";
        //String url0 = "http://192.168.32.60:7010";//内网地址
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
                //.other("x-client-ip", "127.0.0.1")
                //.other("x-source-id", "1")
                .build();
        //获取短信验证码接口
        String body = UrlBuilder.custom()
                .addBuilder("phone", phone)
                .addBuilder("phoneCodeType", "2")
                .fullBody();
        String result = DafaRequest.post(url + "/v1/users/sendPhoneCode", body, headers);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, phone + "," + result);
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
                            //.other("x-tenant-code", "duke")
                            //.other("x-tenant-type", "1")
                            //.other("x-client-ip", "127.0.0.1")
                            //.other("x-source-id", "1")
                            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                            .build();

            String body0 = UrlBuilder.custom()
                    .addBuilder("password", password)
                    .addBuilder("confirmPassword", password)
                    .addBuilder("phone", phone)
                    .addBuilder("code", code)
                    .addBuilder("inviteCode", inviteCode)
                    .fullBody();
            System.out.println(body0);
            HttpConfig httpConfig = HttpConfig.custom()
                    .url(url + "/v1/users/register")
                    .body(body0)
                    .headers(headers0);
            //String result0 = DafaRequest.post(url + "/v1/users/register?", body0, headers);
            String result0 = DafaRequest.post(httpConfig);
            System.out.println("result:" + result0);
        } catch (Exception e) {
            System.out.println(phone + "==============注册失败:");
        }
    }
}
