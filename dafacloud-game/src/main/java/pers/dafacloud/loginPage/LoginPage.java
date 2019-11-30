package pers.dafacloud.loginPage;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import pers.dafacloud.utils.enums.Environment;
import pers.dafacloud.utils.httpUtils.Request;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.randomNameAddrIP.RandomIP;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import sun.misc.BASE64Encoder;

public class LoginPage {


    static Environment environment = Environment.DEFAULT;

    public static void main(String[] args) {

        LoginPage loginPage = new LoginPage();

        //使用密码来获取cookie
        Cookie cookie = loginPage.getDafaCooike("dukepre01","123456");
        //手动添加cookie
        //Cookie cookie =loginPage.produceCookie("JSESSIONID");
        //System.out.println(cookie.getValue());
        //获取棋牌的cookie
        String token = loginPage.getGameToken(cookie);
        System.out.println(token);
    }

    /**
     * 手动添加JSESSIONID生成cookie
     */
    public Cookie produceCookie(String jsessionId) {
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", jsessionId);
        cookie.setVersion(0);
        cookie.setDomain(environment.domain); // 设置范围
        cookie.setPath("/");
        return cookie;
    }

    /**
     * 账号密码登陆 返回cookie
     */
    public Cookie getDafaCooike(String userName, String password) {
        String url = environment.url + "/v1/users/login";
        String body = getLoginBody(userName, password);
        String ip = RandomIP.getRandomIp();
        // 添加头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("User-Agent:", "Mozilla/5.0");
        headers.put("x-forwarded-for", ip);
        headers.put("x-remote-IP", ip);
        headers.put("X-Real-IP", ip);
        headers.put("Origin", environment.url);
        // 获取带cookie的头
//        System.out.println(url);
//        System.out.println(body);
//        String s = DafaRequest.post(url, body);
//        System.out.println(s);
        Map<String, Object> resultMap = Request.doPost(url, body, headers);
        String result = (String) resultMap.get("body");
        System.out.println(result);
        int code  = Integer.parseInt(JSONObject.fromObject(result).get("code").toString());
        if(code!=1){
            System.out.println(body);
            System.out.println(result);
            System.out.println(url);
        }
        List<Cookie> cookies = (List<Cookie>) resultMap.get("cookies");
        Cookie cookie = null;
        for (int i = 0; i < cookies.size(); i++) {
            //System.out.println(i + "-cookies   " + cookies.get(i).getName() + ":" + cookies.get(i).getValue());
            if ("JSESSIONID".equals(cookies.get(i).getName())) {
                cookie = cookies.get(i);
                break;
            }
        }
        return cookie;

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
        //生成登陆的body
        Encoder encoder = Base64.getEncoder();
        //BASE64Encoder encoder = new BASE64Encoder();
        //encoder.decode(data);
        String encode = encoder.encodeToString(random.getBytes());
        //String encode = encoder.encode(random.getBytes());
        //System.out.println(encode);
        String body = String.format("userName=%s&password=%s&random=%s", userName, passwordCode, encode);

        return body;
    }

    /**
     * 获取登陆棋牌使用的Token
     */
    public String getGameToken(Cookie cookie) {
        String url = environment.url + "/v1/game/getGameToken";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("User-Agent:", "Mozilla/5.0");
        headers.put("Origin", environment.url);
        // 获取带cookie的头
        //Map<String, Object> resultMap = Request.doPost(url, null, headers);
        String result = Request.doGet(url, headers, cookie).replace("\\n", "");
        System.out.println(cookie.getValue());
        System.out.println(url);
        System.out.println(result);
        //System.out.println(token);//替换调里面\n的字符串
        JSONObject js = JSONObject.fromObject(result);
        int code = Integer.parseInt(js.getString("code"));
        if(code!=1){
            System.out.println(result);
        }

        return js.getJSONObject("data").get("token").toString();
    }

}
