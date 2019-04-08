package pers.dafacloud.loginPage;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;


import pers.dafacloud.constans.Environment;

import pers.dafacloud.httpUtils.Request;
import sun.misc.BASE64Encoder;


public class LoginPage {


    static Environment environment = Environment.DEFAULT;

    public static void main(String[] args) {

        LoginPage loginPage = new LoginPage();

        //使用密码来获取cookie
        Cookie cookie = loginPage.getDafaCooike("dukea010","123456");
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
        bet(cookie);
        return cookie;
    }

    /**
     * 账号密码登陆 返回cookie
     */
    public Cookie getDafaCooike(String userName, String password) {
        String url = environment.url + "/v1/users/login";
        String body = getLoginBody(userName, password);
        // 添加头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("Origin", environment.url);
        // 获取带cookie的头
        Map<String, Object> resultMap = Request.doPost(url, body, headers);
        String result = (String) resultMap.get("body");
        int code  = Integer.parseInt(JSONObject.fromObject(result).get("code").toString());
        if(code!=1){
            System.out.println(body);
            System.out.println(result);
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
    public String getLoginBody(String userName, String password) {
        //随机码
        String random = "dafacloud_" + Math.random();
        //md5加密后的密码
        String passwordCode = DigestUtils.md5Hex(DigestUtils.md5Hex(userName + DigestUtils.md5Hex(password)) + random);
        //生成登陆的body
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(random.getBytes());
        //System.out.println(encode);
        String body = String.format("userName=%s&password=%s&random=%s", userName, passwordCode, encode);

        return body;
    }

    /**
     * 获取登陆棋牌使用的Token
     */
    public String getGameToken(Cookie cookie) {
        String url = environment.url + "/v1/game/getGameToken?";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("Origin", environment.url);
        // 获取带cookie的头
        //Map<String, Object> resultMap = Request.doPost(url, null, headers);
        String result = Request.doGet(url, null, cookie).replace("\\n", "");

        //System.out.println(token);//替换调里面\n的字符串
        JSONObject js = JSONObject.fromObject(result);
        int code = Integer.parseInt(js.getString("code"));
        if(code!=1){
            System.out.println(result);
        }

        return js.getJSONObject("data").get("token").toString();
    }

    public static void bet(Cookie cookie) {
        String url = environment.url + "/v1/betting/addBetting";
        String body = "bettingData=[{\"lotteryCode\":\"1309\",\"playDetailCode\":\"1303A11\",\"bettingNumber\":\"06 07 08 09 10\",\"bettingCount\":5,\"bettingAmount\":10,\"bettingPoint\":\"7\",\"bettingUnit\":0.005,\"bettingIssue\":\"20190318121\",\"graduationCount\":200}]";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        String s = Request.doPost(url, body, headers, cookie);
        System.out.println(s);
    }




    /*public static  void betTest(Cookie cookie){
        String url = "http://dafacloud-test.com/v1/betting/addBetting";
        String body = "bettingData=[{\"lotteryCode\":\"1309\",\"playDetailCode\":\"1304B11\",\"bettingNumber\":\"01 03,02 04 05 06\",\"bettingCount\":8,\"bettingAmount\":16,\"bettingPoint\":\"2\",\"bettingUnit\":0.001,\"bettingIssue\":\"20190318114\",\"graduationCount\":1000}]";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        //headers.put("Origin", "http://m.dafacloud-test.com");
        String s = Request.doPost(url,body,headers,cookie);
        System.out.println(s);
    }*/

}
