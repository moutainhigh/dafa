package dafagame.testCase.front.profitRank;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

public class ProfitRank {

    private static String profitRank = "/v1/report/userReport/profitRank?type=user";

    @Test(description = "排行榜")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url("/v1/report/userReport/profitRank")
                .addBuilder("type", "user")
                .fullUrl();
        String result = DafaRequest.get(0, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONObject self = jsonResult.getJSONObject("data").getJSONObject("self");
        Log.info("个人排行榜："+self.toString());
    }

}
