package pers.login;

import pers.utils.Md5HA1.AESUtils;
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

    /**
     * 注册请求的加密密码 [MD5(MD5(密码) + SHA1(密码))] ，AES秘钥 = MD5(验证码)
     */
    public static String getPasswordCode(String password, String code) throws Exception {
        return AESUtils.AESEncode(MD5Util.getEncryptionInformation(password), MD5Util.getMd5(code).getBytes());

    }

    //public static void main(String[] args) {
    //    System.out.println(getLoginBody("OTcyMg==","duke123"));
    //
    //}

}
