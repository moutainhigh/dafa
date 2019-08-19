package dafagame.testCase.front.userCenter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;

/**
 * front - 获取已经绑定的银行卡
 */
public class GetBankCardListFront {
    private static String getBankCardListFront = "/v1/users/getBankCardListFront";

    @Test(description = "获取已经绑定的银行卡")
    public static void test01() {
        String result = DafaRequest.get(0, getBankCardListFront);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray data = jsonResult.getJSONArray("data");
        if(data.size()==0){
           Log.info("还未新增银行卡");
        }
        Log.info(String.format("已经绑定的银行卡数量：",data.size()));
        for (int i = 0; i < data.size(); i++) {
            Log.info(data.getString(i));
        }

    }

}
