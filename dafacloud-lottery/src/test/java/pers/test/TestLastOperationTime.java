package pers.test;

import pers.dafacloud.dafaLottery.Login;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;

/**
 * 测试最后操作时间
 */
public class TestLastOperationTime {
    private static String host = "http://caishen02.com";

    public static void main(String[] args) {
        List<String> users = FileUtil.readFile(TestLastOperationTime.class.getResourceAsStream("/test/test01.txt"));
        for (int i = 50; i < 70; i++) {
            HttpConfig httpConfig = Login.loginReturnHttpConfig(users.get(i));
            String body = UrlBuilder
                    .custom()
                    .addBuilder("id", "1403")
                    .addBuilder("amount", "100.18")
                    .addBuilder("paymentType", "12")
                    .addBuilder("remark", "")
                    .fullBody();
            //System.out.println(DafaRequest.get(httpConfig.url(host + "/v1/balance/queryBalanceFront")));
            System.out.println(DafaRequest.post(httpConfig.url(host + "/v1/transaction/rechargeFrontPaymentRecord").body(body)));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
