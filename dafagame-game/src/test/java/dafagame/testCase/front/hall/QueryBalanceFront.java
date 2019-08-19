package dafagame.testCase.front.hall;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 获取余额信息
 * */
public class QueryBalanceFront {

    private static String queryBalanceFront = "/v1/balance/queryBalanceFront";

    @Test(description = "获取余额信息")
    public static void test01() {
        String result = DafaRequest.get(0, queryBalanceFront);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
