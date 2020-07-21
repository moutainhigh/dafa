package pers.utils.dafaCloud;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.net.URL;
import java.util.Base64;

public class DafaCloudLogin {

    /**
     * 获取 加密后的loginBody
     * 账号需要转小写
     *
     * @param userName 用户名
     * @param password 密码
     */
    public static String getLoginBody(String userName, String password) {
        //随机码
        String random = "dafacloud_" + Math.random();
        //md5加密后的密码
        String passwordCode = DigestUtils.md5Hex(DigestUtils.md5Hex(userName + DigestUtils.md5Hex(password)) + random);
        //随机
        String encode = Base64.getEncoder().encodeToString(random.getBytes());
        String body = String.format("userName=%s&password=%s&random=%s", userName, passwordCode, encode);
        return body;
    }

    public static JSONObject getPassword(String userName, String password) {
        //随机码
        String random = "dafacloud_" + Math.random();
        //md5加密后的密码
        String passwordCode = DigestUtils.md5Hex(DigestUtils.md5Hex(userName + DigestUtils.md5Hex(password)) + random);
        //随机
        String encode = Base64.getEncoder().encodeToString(random.getBytes());
        //String body = String.format("userName=%s&password=%s&random=%s", userName, passwordCode, encode);
        JSONObject passwordJson = new JSONObject();
        passwordJson.put("password", passwordCode);
        passwordJson.put("random", encode);
        return passwordJson;
    }

    /**
     * 生成cookie
     *
     * @param JSESSIONID cookie 值
     * @param host       cookie域
     */
    public static BasicClientCookie productCookie(String JSESSIONID, String host) {
        BasicClientCookie basicClientCookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
        try {
            basicClientCookie.setDomain(new URL(host).getHost());
            basicClientCookie.setPath("/");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return basicClientCookie;

    }


}
