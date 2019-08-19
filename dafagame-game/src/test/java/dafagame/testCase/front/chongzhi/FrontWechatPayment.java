package dafagame.testCase.front.chongzhi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 获取微信充值渠道
 * */
public class FrontWechatPayment {
    private static String frontWechatPayment = "/v1/transaction/frontWechatPayment";

    @Test(description = "获取微信充值渠道")
    public static void test01() {

        String result = DafaRequest.get(0,frontWechatPayment);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);

        JSONArray rechargeBankTransferPay = jsonResult.getJSONObject("data").getJSONArray("rechargeWechatPay");
        if (rechargeBankTransferPay.size() == 0) {
            AssertUtil.assertNull(false, "没有微信充值渠道");
        }
        for (int i = 0; i < rechargeBankTransferPay.size(); i++) {
            JSONObject bankTransferPayment = rechargeBankTransferPay.getJSONObject(i);
            System.out.println(bankTransferPayment.getString("id"));
            System.out.println(bankTransferPayment);
        }

    }


}
