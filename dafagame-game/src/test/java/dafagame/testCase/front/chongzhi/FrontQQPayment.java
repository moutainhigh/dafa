package dafagame.testCase.front.chongzhi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 获取QQ充值渠道
 * */
public class FrontQQPayment {

    private static String frontQQPayment = "/v1/transaction/frontQQPayment";

    @Test(description = "获取QQ充值渠道")
    public static void test01() {
        String result = DafaRequest.get(0,frontQQPayment);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);

        JSONArray rechargeBankTransferPay = jsonResult.getJSONObject("data").getJSONArray("rechargeQQPay");
        if (rechargeBankTransferPay.size() == 0) {
            AssertUtil.assertNull(false, "没有QQ充值渠道");
        }
        for (int i = 0; i < rechargeBankTransferPay.size(); i++) {
            JSONObject bankTransferPayment = rechargeBankTransferPay.getJSONObject(i);
            System.out.println(bankTransferPayment.getString("id"));
            System.out.println(bankTransferPayment);
        }

    }
}
