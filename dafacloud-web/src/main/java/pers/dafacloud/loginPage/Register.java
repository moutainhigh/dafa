package pers.dafacloud.loginPage;

import org.apache.commons.codec.digest.DigestUtils;
import pers.dafacloud.httpUtils.Request;

import java.util.HashMap;
import java.util.Map;

public class Register {

    private static  final  String url = "http://app.dfcdn5.com/v1/users/register";

    public static void main(String[] args) {

        for (int i = 6; i < 361 ; i++) {
            registerMeth("dafao"+String.format("%04d", i));
        }
        /*String password = DigestUtils.md5Hex("dafaj0001" + DigestUtils.md5Hex("123456"));
        System.out.println(password);*/
    }



    public static void registerMeth(String userName){
        String password = DigestUtils.md5Hex(userName + DigestUtils.md5Hex("123456"));
        String body = "inviteCode=86232821&userName="+userName+"&password="+password;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        String result = Request.doPostRbody(url,body,headers);

        System.out.println(result);
    }


}
