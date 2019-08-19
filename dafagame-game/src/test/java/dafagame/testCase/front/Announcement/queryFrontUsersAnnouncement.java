package dafagame.testCase.front.Announcement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

/**
 * front - 公告》系统公告
 *
 * */
public class queryFrontUsersAnnouncement {
    private static String queryFrontUsersAnnouncementResultList = "/v1/users/queryFrontUsersAnnouncementResultList";

    @Test(description = "获取系统公告")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(queryFrontUsersAnnouncementResultList)
                .addBuilder("pageSize", "10")
                .addBuilder("pageNum","1")
                .fullUrl();
        String result = DafaRequest.get(0, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray rows  = jsonResult.getJSONObject("data").getJSONArray("rows");
        if (rows.size() == 0) {
            AssertUtil.assertNull(false, "没有系统公告数据");
        }
    }
}
