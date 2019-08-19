package dafagame.testCase.cms.tenant.transactionManage.congZhiManage;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 交易管理 > 一般充值订单中的条件查询中的账户查询(下拉框)（后台GET）
 */
public class GetAccountNameCondition {

    private static String getAccountNameCondition = "/v1/transaction/getAccountNameCondition";

    @Test(description = "充值订单查询条件(账户)")
    public void test001() {
        String result = DafaRequest.get(1,getAccountNameCondition);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
