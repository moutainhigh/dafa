package pers.utils.dafaGameUtils;

import pers.utils.Md5HA1.MD5Util;

import java.util.Base64;

public class DafaGame {

    public static void main(String[] args) {
        //String random = String.valueOf(Math.random());
        //DafaGame.getLoginBody(Base64.getEncoder().encodeToString(random.getBytes()),"duke123");
    }

    /**
     * cocos前台加密
     * 入参：random base64加密数据
     *
     * */
    public static String getLoginBody(String random,String password) {
        String passwordEncode = "";
        try {
            passwordEncode =
                    MD5Util.getMd5(MD5Util.getMd5(MD5Util.getMd5(password) + MD5Util.shaEncode(password)) + random);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passwordEncode;
    }



}
