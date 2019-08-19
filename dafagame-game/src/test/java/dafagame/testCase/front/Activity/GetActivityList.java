package dafagame.testCase.front.Activity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

/**
 * front - 活动》获取所有活动
 */
public class GetActivityList {

    private static String getActivityList = "/v1/activity/getActivityList";

    @Test(description = "获取活动清单")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(getActivityList)
                .addBuilder("type", "1")
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
