package dafagame.testCase.front.Activity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pers.dafagame.utils.report.ZTestReport;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

@Listeners(ZTestReport.class)
public class GetActivityDetails {


    private static String getActivityDetails = "/v1/activity/getActivityDetails";

    @Test(description = "活动对应明细")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(getActivityDetails)
                .addBuilder("id", "109")
                .addBuilder("grade", "1")
                .fullUrl();
        String result = DafaRequest.get(0, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray datas = jsonResult.getJSONArray("data");
        if (datas.size() == 0) {
            AssertUtil.assertNull(false, "没有数据");
        }
        for (int i = 0; i < datas.size(); i++) {
            Log.info(datas.getJSONObject(i).toString());
        }
    }
}
