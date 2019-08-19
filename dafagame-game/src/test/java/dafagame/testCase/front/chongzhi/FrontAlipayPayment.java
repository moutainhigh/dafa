package dafagame.testCase.front.chongzhi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 获取支付宝充值渠道
 */
public class FrontAlipayPayment {

    private static String frontAlipayPayment = "/v1/transaction/frontAlipayPayment";

    @Test(description = "获取支付宝充值渠道")
    public static void test01() {
        String result = DafaRequest.get(0,frontAlipayPayment);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);

        JSONArray rechargeBankTransferPay = jsonResult.getJSONObject("data").getJSONArray("rechargeAlipay");
        if (rechargeBankTransferPay.size() == 0) {
            AssertUtil.assertNull(false, "没有支付宝充值渠道");
        }
        for (int i = 0; i < rechargeBankTransferPay.size(); i++) {
            JSONObject bankTransferPayment = rechargeBankTransferPay.getJSONObject(i);
            System.out.println(bankTransferPayment.getString("id"));
            System.out.println(bankTransferPayment);
        }

    }
}
