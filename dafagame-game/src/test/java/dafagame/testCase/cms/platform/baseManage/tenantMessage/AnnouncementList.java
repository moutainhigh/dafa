package dafagame.testCase.cms.platform.baseManage.tenantMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

public class AnnouncementList {

    private static String announcementList = "/v1/management/manager/announcementList";

    @Test(description = "获取平台消息")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(announcementList)
                .addBuilder("title", "")
                .addBuilder("isTop", "")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray datas = jsonResult.getJSONObject("data").getJSONArray("announcementList");
        Log.info("当页数据量：" + datas.size());
        Log.info("总数据量：" + jsonResult.getJSONObject("data").getInt("total"));
        if (datas.size() == 0) {
            AssertUtil.assertNull(false, "没有数据");
        }
        for (int i = 0; i < datas.size(); i++) {
            Log.info(datas.getJSONObject(i).toString());
        }

    }
}
