package dafagame.testCase.front.chongzhi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
/**
 * 获取银联支付充值渠道
 *
 * */
public class FrontUnionpayPayment {
    private static String frontUnionpayPayment = "/v1/transaction/frontUnionpayPayment";

    @Test(description = "获取银联支付充值渠道")
    public static void test01() {

        String result = DafaRequest.get(0,frontUnionpayPayment);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);

        JSONArray rechargeBankTransferPay = jsonResult.getJSONObject("data").getJSONArray("rechargeUnionPay");
        if (rechargeBankTransferPay.size() == 0) {
            AssertUtil.assertNull(false, "没有银联支付充值渠道");
        }
        for (int i = 0; i < rechargeBankTransferPay.size(); i++) {
            JSONObject bankTransferPayment = rechargeBankTransferPay.getJSONObject(i);
            System.out.println(bankTransferPayment.getString("id"));
            System.out.println(bankTransferPayment);
        }

    }

}
