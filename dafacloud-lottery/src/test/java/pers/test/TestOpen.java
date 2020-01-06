package pers.test;

import org.apache.http.Header;
import org.springframework.util.DigestUtils;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.util.Date;

public class TestOpen {
    //private static String hexString = "weiwei";

    /**
     * 手动开奖接口
     */
    public static void main(String[] args) {
        function02();
    }

    public static void function02() {

        String url = "http://52.76.195.164:8051/v1/lottery/spider/insertLotteryOpenNumber?insertFrom=kcw&hexStr="  + "&insertTime=";
        Header[] headers = HttpHeader.custom()
                //.contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .contentType("application/json")
                .build();
        String body =
                UrlBuilder.custom()
                        //.addBuilder(String.format("[{\"issue\":\"20191218076\",\"lotteryCode\":\"1309\",\"open\":false,\"openNumber\":\"01,06,04,03,10,05,08,09,07,02\",\"openTime\":%s}]",insertTime))
                        //.addBuilder(String.format("[{\"issue\":\"989801\",\"lotteryCode\":\"1302\",\"open\":false,\"openNumber\":\"04,07,10,12,15,16,23,28,29,30,32,40,46,48,50,55,71,74,75,78+00\",\"openTime\":%s}]",insertTime))
                        .addBuilder("[{\"issue\":\"201912181130\",\"lotteryCode\":\"1407\",\"open\":false,\"openNumber\":\"1,2,3\",\"openTime\":1576152354000}]")
                        .fullBody();
        HttpConfig httpConfig = HttpConfig.custom()
                .url(url)
                .body(body)
                .headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(result);
    }
}
