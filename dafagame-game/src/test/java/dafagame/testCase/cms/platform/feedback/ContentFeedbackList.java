package dafagame.testCase.cms.platform.feedback;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

public class ContentFeedbackList {
    private static String contentFeedbackList = "/v1/management/content/contentFeedbackList";

    //-- 故障反馈 记录
    //-- source_id 1:android 2:ios 3:web-android 4:web-ios
    //-- mark_type 0:未標記 1:需求 2:bug 3:無效
    @Test(description = "获取故障反馈")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(contentFeedbackList)
                .addBuilder("userName", "")
                .addBuilder("version", "")
                .addBuilder("startDate", "")
                .addBuilder("endDate", "")
                .addBuilder("markType", "") //0:未標記 1:需求 2:bug 3:無效
                .addBuilder("pageNumber", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray datas = jsonResult.getJSONObject("data").getJSONArray("list");
        if (datas.size() == 0) {
            AssertUtil.assertNull(false, "没有数据");
        }
        Log.info("当前页数据量：" + datas.size());
        Log.info("总数据量：" + jsonResult.getJSONObject("data").getInt("total"));
        for (int i = 0; i < datas.size(); i++) {
            Log.info(datas.getJSONObject(i).toString());
        }
    }
}
