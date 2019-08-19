package dafagame.testCase.front.chongzhi;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 获取银联转账充值渠道
 * */
public class FrontBankTransferPayment {

    private static String frontBankTransferPayment = "/v1/transaction/frontBankTransferPayment";

    @Test(description = "获取银联转账充值渠道")
    public static void test0101() {
        String result = DafaRequest.get(0,frontBankTransferPayment);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);

        JSONArray rechargeBankTransferPay = jsonResult.getJSONObject("data").getJSONArray("rechargeBankTransferPay");
        if(rechargeBankTransferPay.size()==0){
            AssertUtil.assertNull(false,"没有银行转账充值渠道");
        }
        for (int i = 0; i < rechargeBankTransferPay.size(); i++) {
            JSONObject bankTransferPayment = rechargeBankTransferPay.getJSONObject(i);
            System.out.println(bankTransferPayment.getString("branchName"));
            System.out.println(bankTransferPayment);
        }
    }
}
