package dafagame.common;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;


public class RegisterDafaGame {

    //private static  final  String url = "http://192.168.8.193:7010/v1/users/register";
    //private static  final  String url = "http://caishen03.com/v1/users/register"; //lotterypre
    private static final String url = "http://duke.dafagame-test.com:86";//棋牌系统海棠站
    private static HttpConfig httpConfig = HttpConfig.custom();
    private static Header[] headers = HttpHeader
            .custom()
            .other("x-tenant-code", "duke")
            .other("x-source-id", "1")
            .other("x-client-ip", RandomIP.getRandomIp())
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .build();

    public static void main(String[] args) {
        //设置header
        httpConfig.headers(HttpHeader.custom().contentType("text/plain;charset=UTF-8").build());

        final String phone = "13612346603";
        String inviteCode = "2643153";//邀请码 2643153 棋牌系统dukea站 //13612346601  13612346602 13612346603
        try {
            registerMeth(url, phone, inviteCode);
        } catch (Exception e) {
            System.out.println(phone + "==============注册失败:");
        }

        //System.out.println(DigestUtils.md5Hex("1361234")+06601);//3216e4b814ed1d5d91f34c5ea6e443c06601

    }

    /**
     * 棋牌系统注册功能
     */
    public static void registerMeth(String url, String phone, String inviteCode) {
        //获取手机验证码
        String code = SendPhoneCode.getPhoneCode(phone, "2");
        System.out.println(code);
        String password = DigestUtils.md5Hex("duke123");//MD5加密一次
        String body = UrlBuilder.custom()
                .addBuilder("password", password)
                .addBuilder("confirmPassword", password)
                .addBuilder("phone", phone)
                .addBuilder("code", code)
                .addBuilder("inviteCode", inviteCode)
                .fullBody();
        httpConfig.url(url + "/v1/users/register?");//要加body棋牌是要加body
        String result = DafaRequest.post(httpConfig.body(body).headers(headers));
        System.out.println("result:" + result);
    }

    @Test(description = "测试")
    public static void test01() {
        String phone = "13012345";
        for (int i = 100; i < 200; i++) {
            try {
                registerDafaGame(String.format("%s%03d", phone, i));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    //内网注册，棋牌游戏账号，随机ip
    public static void registerDafaGame(String phone) {
        String url = "http://192.168.32.45:7010";//内网地址
        //String phone = "13012345682";//手机号
        String inviteCode = "2336755";//邀请码
        Header[] headers = HttpHeader
                .custom()
                .other("x-tenant-code", "duke")
                .other("x-source-id", "1")
                .other("x-tenant-type", "1")
                .other("x-client-ip", RandomIP.getRandomIp())
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .build();

        //获取验证码接口
        String body = UrlBuilder.custom()
                .addBuilder("phone", phone)
                .addBuilder("phoneCodeType", "2")
                .fullBody();
        String result = DafaRequest.post(url + "/v1/users/sendPhoneCode", body, headers);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, phone + "," + result);
        String code = jsonResult.getString("data");//短信验证码
        //注册 接口
        try {
            //System.out.println(code);
            String password = DigestUtils.md5Hex("duke123");//MD5加密一次
            String body0 = UrlBuilder.custom()
                    .addBuilder("password", password)
                    .addBuilder("confirmPassword", password)
                    .addBuilder("phone", phone)
                    .addBuilder("code", code)
                    .addBuilder("inviteCode", inviteCode)
                    .fullBody();
            System.out.println(body0);
            String result0 = DafaRequest.post(url + "/v1/users/register?", body0, headers);
            System.out.println("result:" + result0);
        } catch (Exception e) {
            System.out.println(phone + "==============注册失败:");
        }

    }
}
