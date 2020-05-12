package pers.dafagame.test;

import pers.dafagame.dafagameUtils.Constants;
import pers.dafagame.dafagameUtils.Login;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestFrontGate {
    private static String host = Constants.HOST;
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);
    private static String queryBalanceFront = host + "/v1/balance/queryBalanceFront";

    /**
     * 测试前台 网关重启 是否会分发到其他机器
     */
    public static void main(String[] args) {
        Login.Login("98163415", "123qwe");

        List<String> userAccounts = FileUtil.readFile(TestFrontGate.class.getResourceAsStream("/dukePhone.txt"));
        for (int i = 0; i < 200; i++) {
            String userAccount = userAccounts.get(i);
            excutors.execute(() -> getRequest(userAccount));
            try {
                Thread.sleep(100 * 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 请求
     */
    public static void getRequest(String userAccount) {
        HttpConfig httpConfig = Login.Login(userAccount, "123qwe");
        httpConfig.url(queryBalanceFront);
        for (int i = 0; i < 100000000; i++) {
            System.out.println(DafaRequest.get(httpConfig));
            try {
                Thread.sleep(1000 * 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
