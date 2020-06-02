package pers.test;

import pers.dafacloud.constant.LotteryConstant;
import pers.dafacloud.betRun.Betting;
import pers.dafacloud.dafaLottery.Login;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RechargeFrontPayment {
    private static String host = LotteryConstant.host;

    private static String rechargeFrontPaymentRecord = host + "/v1/transaction/rechargeFrontPaymentRecord";

    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    public static void draw(String user, int n) {
        HttpConfig httpConfig0 = Login.loginReturnHttpConfig(user);//登录
        //充值申请
        for (int i = 0; i < 10; i++) {
            //String body0 = "bankCardID=10000238&amount=100&safetyPassword=a6773873bca3f8faef29f3f7fee0cbdd"; 100200
            String body = UrlBuilder
                    .custom()
                    .addBuilder("id", "10151")
                    .addBuilder("amount", "166")
                    .addBuilder("paymentType", "3")
                    .addBuilder("remark", "测试")
                    .fullBody();
            System.out.println(DafaRequest.post(httpConfig0.url(rechargeFrontPaymentRecord).body(body)));
            try {
                Thread.sleep(n * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 按用户数量创建线程
     */
    private static void bettingByUrlLoop0(List<String> users, int n) {
        //for (String user : users) {
        for (int i = 0; i < 112; i++) {
            System.out.println(i + "------------------------------------------------------------------");
            int ii = i;
            excutors.execute(() -> draw(users.get(ii), n));
            try {
                Thread.sleep(5 * 1000); //每隔秒登录一个用户
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        List<String> listusers = FileUtil.readFile(Betting.class.getResourceAsStream("/users/dev1Dafa.txt"));
        bettingByUrlLoop0(listusers, 6);
    }
}
