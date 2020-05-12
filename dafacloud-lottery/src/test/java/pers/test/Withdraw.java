package pers.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;
import pers.dafacloud.constant.LotteryConstant;
import pers.dafacloud.dafaLottery.Betting;
import pers.dafacloud.dafaLottery.Login;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Withdraw {
    private static String host = LotteryConstant.host;

    private static String saveFrontWithdrawRecord = host + "/v1/transaction/saveFrontWithdrawRecord";

    private static String frontWithdrawRecord = host + "/v1/transaction/frontWithdrawRecord";

    private static String setSafetyPassword = host + "/v1/users/setSafetyPassword";
    private static String verifySafetyPassword = host + "/v1/users/verifySafetyPassword";
    private static String addBankCard = host + "/v1/users/addBankCard";

    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    public static void draw(String user, int n) {
        HttpConfig httpConfig0 = Login.loginReturnHttpConfig(user);//登录
        //设置安全密码
        System.out.println(DafaRequest.post(httpConfig0.url(setSafetyPassword).body("safetyPassword=a6773873bca3f8faef29f3f7fee0cbdd")));
        ////验证安全密码
        System.out.println(DafaRequest.post(httpConfig0.url(verifySafetyPassword).body("safetyPassword=a6773873bca3f8faef29f3f7fee0cbdd&verifyType=bankcard")));
        //添加银行卡
        int newNum = (int) ((Math.random() * 9 + 1) * 100000000);
        String bankCardNumber = "622" + newNum;
        System.out.println(user + " - " + bankCardNumber);
        String addBankCardBody = UrlBuilder.custom()
                .addBuilder("bankName", "招商银行")
                .addBuilder("accountName", "杜克")
                .addBuilder("province", "北京市")
                .addBuilder("city", "北京市")
                .addBuilder("bankCardNumber", bankCardNumber)
                .addBuilder("isOtherBank", "false")
                .fullBody();
        System.out.println(DafaRequest.post(httpConfig0.url(addBankCard).body(addBankCardBody)));

        //获取银行卡id
        String resultFrontWithdrawRecord = DafaRequest.get(httpConfig0.url(frontWithdrawRecord));
        String id = "";
        try {
            JSONArray userBankCardList = JSONObject.parseObject(resultFrontWithdrawRecord).getJSONObject("data").getJSONArray("userBankCardList");
            for (int i = 0; i < userBankCardList.size(); i++) {
                JSONObject userBankCard = userBankCardList.getJSONObject(i);
                if (!userBankCard.getBoolean("isDisable")) {
                    id = userBankCard.getString("id");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("resultFrontWithdrawRecord：" + resultFrontWithdrawRecord);
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(id)) {
            System.out.println("提取银行卡id空");
            return;
        }
        //提现申请
        for (int i = 0; i < 10; i++) {
            //String body0 = "bankCardID=10000238&amount=100&safetyPassword=a6773873bca3f8faef29f3f7fee0cbdd"; 100200
            String body = UrlBuilder
                    .custom()
                    .addBuilder("bankCardID", id)
                    .addBuilder("amount", "166")
                    .addBuilder("safetyPassword", "a6773873bca3f8faef29f3f7fee0cbdd")
                    .fullBody();
            System.out.println(DafaRequest.post(httpConfig0.url(saveFrontWithdrawRecord).body(body)));
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
        for (int i = 108; i < 112; i++) {
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

    @Test(description = "测试")
    public static void test01() {
        for (int i = 0; i < 100; i++) {
            //System.out.println(Math.random() * 9);
            int newNum = (int) ((Math.random() * 9 + 1) * 100000000);
            System.out.println(newNum);
        }
    }
}
