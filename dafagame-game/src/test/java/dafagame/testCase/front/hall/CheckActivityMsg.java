package dafagame.testCase.front.hall;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;

public class CheckActivityMsg {

    private static String checkActivityMsg = "/v1/activity/checkActivityMsg";

    @Test(description = "测试")
    public static void test01() {
        String result = DafaRequest.get(0, checkActivityMsg);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }

}
