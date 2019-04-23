package pers.dafacloud.loginPage;

import org.apache.commons.codec.digest.DigestUtils;
import pers.dafacloud.httpUtils.Request;

import java.util.HashMap;
import java.util.Map;

public class RegisterThread extends Thread {

    private static  final  String url = "http://app.dfcdn5.com/v1/users/register";
    private int start;
    private int end;

    public RegisterThread(int start,int end){
        this.start=start;
        this.end = end;
    }

    public static void main(String[] args) {
        //System.out.println();
        //RegisterThread registerThread =new RegisterThread();
        for (int i = 1; i < 2 ; i++) {
            try {
                //registerMeth("autoa"+String.format("%05d", i));
            } catch (Exception e) {
                System.out.println("注册失败:"+"autoa"+String.format("%04d", i));
                //e.printStackTrace();
            }

        }
        //String password = DigestUtils.md5Hex("dafai0002" + DigestUtils.md5Hex("123456"));
        //System.out.println(password);
    }



    public void run() {
//        String password = DigestUtils.md5Hex(userName + DigestUtils.md5Hex("123456"));
//        String body = "inviteCode=40924811&userName="+userName+"&password="+password;
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//        String result = Request.doPostRbody(url,body,headers);
//        System.out.print(userName);
    }
}
