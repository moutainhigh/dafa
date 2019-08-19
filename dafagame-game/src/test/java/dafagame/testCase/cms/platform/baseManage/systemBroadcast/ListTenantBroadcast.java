package dafagame.testCase.cms.platform.baseManage.systemBroadcast;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 系统广播
 */
public class ListTenantBroadcast {
    private static String listTenantBroadcast = "/v1/management/tenant/listTenantBroadcast";

    //broadcast_type 1游戏大厅 2游戏房间 3后台

    @Test(description = "获取系统广播列表")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(listTenantBroadcast)
                .addBuilder("broadcastType", "")//广播类型 1游戏大厅 2游戏房间 3后台
                .addBuilder("broadcastState", "") //广播状态 1广播中，2已过期
                .addBuilder("pageNum", "1")
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
