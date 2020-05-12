package dafagame.testCase.cms.platform.baseManage.tenantManage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

public class GetTenantMaintenanceList {

    private static String getTenantMaintenanceList = "/v1/management/tenant/getTenantMaintenanceList";

    @Test(description = "获取维护设置")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(getTenantMaintenanceList)
                .addBuilder("tenantCode.txt", "duke")
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);

        JSONArray datas = jsonResult.getJSONObject("data").getJSONArray("list");
        if (datas.size() == 0) {
            AssertUtil.assertNull(false, "没有数据");
        }
        Log.info("当前页数据量："+datas.size());
        for (int i = 0; i < datas.size(); i++) {
            Log.info(datas.getJSONObject(i).toString());
        }

    }

}
