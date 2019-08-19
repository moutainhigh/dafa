package dafagame.common;

import org.apache.commons.codec.digest.DigestUtils;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;


public class Login {
    static String loginPath = "/v1/users/login";

    public static void main(String[] args) {
        loginDafaGame("95231885");
        loginDafaGame("94790768");
        loginDafaGame("60398442");
    }

    /**
     * 登陆棋牌 ，账号密码登陆，前台
     */
    public static String loginDafaGame(String phone) {
        //随机邀请码
        String random = "9722";
        //Encoder encoder = Base64.getEncoder();
        //String encode = encoder.encodeToString(random.getBytes());
        String encodeRandom = "OTcyMg==";
        //body
        String body = UrlBuilder.custom()
                .addBuilder("inviteCode", "")
                .addBuilder("accountNumber", phone)
                .addBuilder("password", DigestUtils.md5Hex("duke123") + encodeRandom) //"b4e82b683394b50b679dc2b51a79d987"
                .addBuilder("userType", "0") //正式0/测试1/遊客2
                .addBuilder("random", random)
                .fullBody();
        //String body = "inviteCode=&accountNumber="+phone+"&password=b4e82b683394b50b679dc2b51a79d987"+encodeRandom+"&userType=1&random="+random;
        String result = DafaRequest.post(loginPath, body);
//        System.out.println(body);
        System.out.println(result);
        return result;
    }
}
