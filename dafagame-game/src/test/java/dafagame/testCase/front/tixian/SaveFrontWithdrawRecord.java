package dafagame.testCase.front.tixian;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import dafagame.common.VerifySafetyPassword;
import pers.dafagame.utils.report.ZTestReport;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.propertiesUtils.PropertiesUtil;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现保存接口
 */
@Listeners(ZTestReport.class)
public class SaveFrontWithdrawRecord {
//    public static void main(String[] args) {
//        System.out.println(PropertiesUtil.getProperty("safetyPassword").length()>10
//                ?DigestUtils.md5Hex(PropertiesUtil.getProperty("safetyPassword")):PropertiesUtil.getProperty("safetyPassword"));
//    }

    private static String saveFrontWithdrawRecord = "/v1/transaction/saveFrontWithdrawRecord";
    private static String safetyPassword = DigestUtils.md5Hex(PropertiesUtil.getProperty("safetyPassword"));
    private static List<Integer> cardId = new ArrayList<>();
    private static String cardIdIsDisable; //已经冻结的银行卡
    private static int withdrawMaxAmount;
    private static int withdrawMinAmount;

    @BeforeClass
    public static void beforCalsee() {
        String result = FrontWithdrawRecord.get(); //
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONObject data = jsonResult.getJSONObject("data");
        Log.info(String.format("当前剩余可提现次数：%s", data.get("withdrawRemainTimes")));
        JSONObject payLimit = data.getJSONObject("payLimit");
        withdrawMaxAmount = payLimit.getInt("withdrawMaxAmount");//最大可提现金额
        withdrawMinAmount = payLimit.getInt("withdrawMinAmount");//最小可提现金额
        Log.info(String.format("可提现最大金额：%s", withdrawMaxAmount));
        Log.info(String.format("可提现最小金额：%s", withdrawMinAmount));
        JSONArray ja = data.getJSONArray("userBankCardList");
        if (ja.size() == 0) {
            Log.info("未绑定银行卡");
            return;
        }
        Log.info("银行卡张数:" + ja.size());
        for (int i = 0; i < ja.size(); i++) {
            //Log.info(ja.getJSONObject(i).toString());
            if (!ja.getJSONObject(i).getBoolean("isDisable"))
                cardId.add(ja.getJSONObject(i).getInt("id"));
            else
                cardIdIsDisable = ja.getJSONObject(i).getString("id");
        }
        Log.info(String.format("银行卡id数组：%s", cardId));
        Log.info(String.format("冻结银行卡id：%s", cardIdIsDisable));
    }

    @Test(description = "提现成功")
    public void test001() {
        VerifySafetyPassword.verify("withdraw");//验证安全密码
        if (cardId == null) {
            throw new RuntimeException("未绑定银行卡，无法提现");
        }
        //String body = "bankCardID=1&amount=100&safetyPassword=93279e3308bdbbeed946fc965017f67a";
        int random = (int) (Math.random() * (cardId.size()));
        //System.out.println(random);
        String body = UrlBuilder.custom()
                .addBuilder("bankCardID", String.valueOf(cardId.get(random)))
                .addBuilder("amount", "100")
                .addBuilder("safetyPassword", "93279e3308bdbbeed946fc965017f67a")
                .fullBody();
        String result = DafaRequest.post(0,saveFrontWithdrawRecord, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);

    }

    @Test(description = "提现银行卡id不存在")
    public void test002() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VerifySafetyPassword.verify("withdraw");//验证安全密码
        String body = "bankCardID=19999&amount=200&safetyPassword=93279e3308bdbbeed946fc965017f67a";
        String result = DafaRequest.post(0,saveFrontWithdrawRecord, body);
        AssertUtil.assertContains(result, "操2作异常");
    }

    @Test(description = "提现的银行卡已经冻结")
    public void test003() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VerifySafetyPassword.verify("withdraw");//验证安全密码
        String body = UrlBuilder.custom()
                .addBuilder("bankCardID", cardIdIsDisable)
                .addBuilder("amount", "100")
                .addBuilder("safetyPassword", "93279e3308bdbbeed946fc965017f67a")
                .fullBody();
        AssertUtil.assertNull(cardIdIsDisable!=null, "cardIdIsDisable:"+cardIdIsDisable);
        String result = DafaRequest.post(0,saveFrontWithdrawRecord, body);
        AssertUtil.assertContains(result, "已被禁用");

    }

    @Test(description = "大于该充值渠道的可提现值金额")
    public void test004() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VerifySafetyPassword.verify("withdraw");//验证安全密码
        int random = (int) (Math.random() * (cardId.size()));
        String body = UrlBuilder.custom()
                .addBuilder("bankCardID", String.valueOf(cardId.get(random)))
                .addBuilder("amount", String.valueOf(withdrawMaxAmount + 10))
                .addBuilder("safetyPassword", "93279e3308bdbbeed946fc965017f67a")
                .fullBody();
        String result = DafaRequest.post(0,saveFrontWithdrawRecord, body);
        AssertUtil.assertContains(result, "金额必须在");
    }

    @Test(description = "小于该充值渠道的可提现值金额")
    public void test005() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VerifySafetyPassword.verify("withdraw");//验证安全密码
        int random = (int) (Math.random() * (cardId.size()));
        String body = UrlBuilder.custom()
                .addBuilder("bankCardID", String.valueOf(cardId.get(random)))
                .addBuilder("amount", String.valueOf((withdrawMinAmount - 10) > 0 ? (withdrawMinAmount - 10) : 10))
                .addBuilder("safetyPassword", "93279e3308bdbbeed946fc965017f67a")
                .fullBody();
        String result = DafaRequest.post(0,saveFrontWithdrawRecord, body);
        AssertUtil.assertContains(result, "金额必须在");
    }

    /*@Test(description = "等于该充值渠道的可提现值最大金额")
    public void test006() {
        String body = "bankCardID=11636385&amount=200&safetyPassword=93279e3308bdbbeed946fc965017f67a";
        String s = DafaRequest.post(0,saveFrontWithdrawRecord, body);
        System.out.println(s);
        Reporter.log(s);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    @Test(description = "等于该充值渠道的可提现值最小金额")
    public void test007() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VerifySafetyPassword.verify("withdraw");//验证安全密码
        int random = (int) (Math.random() * (cardId.size()));
        String body = UrlBuilder.custom()
                .addBuilder("bankCardID", String.valueOf(cardId.get(random)))
                .addBuilder("amount", String.valueOf(withdrawMinAmount))
                .addBuilder("safetyPassword", "93279e3308bdbbeed946fc965017f67a")
                .fullBody();
        String result = DafaRequest.post(0,saveFrontWithdrawRecord, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
    }

    @Test(description = "验证安全密码ok,提现密码NG")
    public void test010() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VerifySafetyPassword.verify("withdraw");//验证安全密码
        int random = (int) (Math.random() * (cardId.size()));
        String body = UrlBuilder.custom()
                .addBuilder("bankCardID", String.valueOf(cardId.get(random)))
                .addBuilder("amount", String.valueOf(withdrawMinAmount))
                .addBuilder("safetyPassword", "93279e3308bdbbeed946fc96501duke")
                .fullBody();
        String result = DafaRequest.post(0,saveFrontWithdrawRecord, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
    }
}
