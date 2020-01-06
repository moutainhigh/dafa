package pers.utils.dafaGame;

import pers.utils.Md5HA1.MD5Util;

public class DafaGameLogin {
    /**
     * cocos前台加密
     *
     * @param random   base64加密随机数
     * @param password 登录密码
     */
    public static String getLoginBody(String random, String password) {
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
