package pers.test.Wheels;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunPrizes {
    private static ExecutorService executors = Executors.newFixedThreadPool(300);

    private static final String HOST = "http://52.76.195.164";
    private static final String lotteryDraw = HOST + ":8080/v1/activity/prizeWheels/lotteryDraw";


    private static void getPrize(String userName, String userId) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-tenant-code", "dafa")
                .other("x-source-Id", "3")
                .other("x-user-id", userId)
                .other("x-user-name", userName)
                .other("x-client-ip", "118.143.214.129")
                .other("x-is-test", "0")
                .other("x-url", "dafacloud.com")
                .build();

        HttpConfig httpConfig = HttpConfig.custom()
                .url(lotteryDraw)
                .headers(headers);
        for (int i = 0; i < 257; i++) {
            String result = DafaRequest.get(httpConfig);
            System.out.println(userName + " - " + result);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public static void runBetting() {
        List<String> userList = FileUtil.readFile("/Users/duke/Documents/dafaUsers/dev1dafa.txt");
        //for (int i = 0; i < userList.size(); i++) {
        for (int i = 0; i < 100; i++) {
            String[] s = userList.get(i).split(",");
            executors.execute(() -> {
                getPrize(s[1], s[0]);
            });
        }
    }

    public static void main(String[] args) {
        //getPrize("dafai0001","50000511");
        //getPrize("wheel2","60960961");
        runBetting();
    }
}
