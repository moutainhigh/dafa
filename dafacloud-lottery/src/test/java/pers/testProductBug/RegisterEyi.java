package pers.testProductBug;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;

public class RegisterEyi {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            register(i);
            Thread.sleep(60*100);
        }
    }

    public static  void register(int i){
        String ip = RandomIP.getRandomIp();
        String URL = "http://caishen02.com/v1/users/register";
        String body = String.format("inviteCode=16557652&userName=duketest%03d&password=effed2b4b434447c335e72fb7a0cc9bf&ImgCode=nxhl",i);
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                //.other("x-forwarded-for", ip)
                //.other("x-remote-IP", ip)
                //.other("X-Real-IP", ip)
                .build();
        HttpConfig httpConfig = HttpConfig.custom().url(URL).body(body).headers(headers);
        String result0 = DafaRequest.post(httpConfig);
        System.out.println(result0);
    }

}
