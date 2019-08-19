package dafagame.testCase.front.tixian;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pers.dafagame.utils.report.ZTestReport;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现展示接口
 * 包含最小和最大可提现金额，可提现次数
 * 可提现银行卡列表
 * 余额
 */
public class FrontWithdrawRecord {
    private static String frontWithdrawRecord = "/v1/transaction/frontWithdrawRecord";

    private static List<Integer> cardId = new ArrayList<>();
    private static String cardIdIsDisable; //已经冻结的银行卡
    private static int withdrawMaxAmount;
    private static int withdrawMinAmount;

    public static void main(String[] args) {
        System.out.println(get());
    }

    /**
     * 获取绑定的银行卡id，用于提现
     */
    public static String get() {
        String result = DafaRequest.get(0,frontWithdrawRecord);
        return result;
    }

    /**
     * withdrawRemainTimes 剩余可提现次数
     */
    @Test(priority = 1, description = "提现页面展示")
    public void test001() {
        String result = DafaRequest.get(0,frontWithdrawRecord);
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

}
