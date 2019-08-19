package dafagame.testCase.front.hall;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.jsonUtils.JsonOfObject;
import pers.utils.jsonUtils.Response;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 大厅 > 基础信息
 */
public class QueryBaseLoginInfo {
    private static String queryBaseLoginInfo = "/v1/users/queryBaseLoginInfo";

    @Test(description = "获取基础信息")
    public static void test01() {
        String result = DafaRequest.get(0, queryBaseLoginInfo);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
