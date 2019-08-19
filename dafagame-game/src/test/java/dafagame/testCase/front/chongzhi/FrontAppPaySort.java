package dafagame.testCase.front.chongzhi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;

/**
 * 充值排序列表
 *
 * */
public class FrontAppPaySort {
    private static String frontAppPaySort = "/v1/transaction/frontAppPaySort";

    @Test(description = "充值渠道")
    public static void test01() {
        String result = DafaRequest.get(0,frontAppPaySort);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);

        JSONArray rechargeBankTransferPay = jsonResult.getJSONObject("data").getJSONArray("rechargeSort");
        if (rechargeBankTransferPay.size() == 0) {
            AssertUtil.assertNull(false, "没有充值渠道");
        }
        Log.info("数据量："+rechargeBankTransferPay.size());
        for (int i = 0; i < rechargeBankTransferPay.size(); i++) {
            JSONObject bankTransferPayment = rechargeBankTransferPay.getJSONObject(i);
            System.out.println(bankTransferPayment);
        }
    }
}
