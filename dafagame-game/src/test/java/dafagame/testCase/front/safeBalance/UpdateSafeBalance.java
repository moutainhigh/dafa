package dafagame.testCase.front.safeBalance;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 保险箱存入和取出
 */
public class UpdateSafeBalance {

    private static String updateSafeBalance = "/v1/balance/updateSafeBalance";

    @Test(description = "存入")
    public static void test0101() {
        String body = UrlBuilder.custom()
                .addBuilder("inOrOut", "1") //1存入保险箱，2保险箱取出
                .addBuilder("amount", "99")
                .addBuilder("safetyPassword", "22cd25cd93064ce0a6a738cb248ca1ae")
                .fullBody();
        String result = DafaRequest.post(0,updateSafeBalance, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }

    @Test(description = "取出")
    public static void test0201() {
        String body = UrlBuilder.custom()
                .addBuilder("inOrOut", "2") //1存入保险，2保险箱取出
                .addBuilder("amount", "72")
                .addBuilder("safetyPassword", "22cd25cd93064ce0a6a738cb248ca1ae")
                .fullBody();
        String result = DafaRequest.post(0,updateSafeBalance, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
    }
}
