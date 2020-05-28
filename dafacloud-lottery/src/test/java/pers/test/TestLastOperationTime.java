package pers.test;

import pers.dafacloud.dafaLottery.Login;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;

import java.util.List;

/**
 * 测试最后操作时间
 */
public class TestLastOperationTime {
    private static String host = "http://caishen02.com";

    public static void main(String[] args) {
        List<String> users = FileUtil.readFile(TestLastOperationTime.class.getResourceAsStream("/test/test01.txt"));
        for (int i = 453; i < users.size(); i++) {

            HttpConfig httpConfig = Login.loginReturnHttpConfig(users.get(i));
            System.out.println(DafaRequest.get(httpConfig.url(host + "/v1/balance/queryBalanceFront")));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }

}
