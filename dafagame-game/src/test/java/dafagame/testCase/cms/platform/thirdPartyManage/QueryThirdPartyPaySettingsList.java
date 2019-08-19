package dafagame.testCase.cms.platform.thirdPartyManage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

public class QueryThirdPartyPaySettingsList {
    private static String queryThirdPartyPaySettingsList = "/v1/transaction/queryThirdPartyPaySettingsList";

    @Test(description = "获取第三方管理")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(queryThirdPartyPaySettingsList)
                .addBuilder("thirdPartyName", "")
                .addBuilder("startTime", "")
                .addBuilder("endTime", "")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray datas = jsonResult.getJSONObject("data").getJSONArray("rows");
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
