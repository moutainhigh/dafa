package pers.dafacloud.page.pageLogin;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.utils.dafaRequest.DafaRequest;

import java.util.Base64;

public class Login {

    private final static Logger Log = LoggerFactory.getLogger(Login.class);

    //static Path loginPath = Path.login;

    private static  String loginPath = "/v1/users/login";

    public static void main(String[] args) {
        Login loginPage = new Login();
        //使用密码来获取cookie
        loginPage.loginDafaCloud("duke0112","123456");
        //获取棋牌的token
        //String token = loginPage.getGameToken();
        //System.out.println(token);
    }

    /**
     * 彩票前台，账号密码登陆 返回cookie
     */
    public  String loginDafaCloud(String userName, String password) {
        String body = getLoginBody(userName, password);
        // 获取带cookie的头
        String result = DafaRequest.post(loginPath, body);
        System.out.println(result);
        int code  = Integer.parseInt(JSONObject.fromObject(result).get("code").toString());
        Log.info(userName+":"+result);
        if(code!=1){
            Log.info("登陆失败:"+body);
        }
        return result;
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

    /**
     * 获取登陆棋牌使用的Token
     */
    public String getGameToken() {
        String url =  "/v1/game/getGameToken?";
        // 取带cookie的头
        String result = DafaRequest.get(url).replace("\\n", "");//替换调里面\n的字符串
        JSONObject js = JSONObject.fromObject(result);
        int code = Integer.parseInt(js.getString("code"));
        if(code!=1){
            System.out.println(result);
        }

        return js.getJSONObject("data").get("token").toString();
    }
}
