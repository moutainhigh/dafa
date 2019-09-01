package dafagame.testCase.cms.tenant.tenantManage.congZhiManage.bankTransfer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;

/**
 * 银行转账充值渠道 ，获取数据
 */
public class BankTransferList {

    private static String bankTransferList = "/v1/transaction/bankTransferList";

    @Test(description = "获取现有的银行转账渠道")
    public static void test01() {
        String result = DafaRequest.get(1,bankTransferList);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
        JSONArray ja = jsonResult.getJSONArray("data");
        for (int i = 0; i < ja.size(); i++) {
            Log.info(ja.get(i).toString());
        }
    }

}
