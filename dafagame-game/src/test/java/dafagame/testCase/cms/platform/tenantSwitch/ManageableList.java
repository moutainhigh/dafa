package dafagame.testCase.cms.platform.tenantSwitch;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

public class ManageableList {

    private static String manageableList = "/v1/management/manager/manageableList";

    //1	PROXY	直营
    //2	DIRECT	渠道
    @Test(description = "获取厅主")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(manageableList)
                .addBuilder("name", "")
                .addBuilder("tenantType", "")
                .addBuilder("tenantCode", "")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray datas = jsonResult.getJSONObject("data").getJSONArray("tenantList");
        if (datas.size() == 0) {
            AssertUtil.assertNull(false, "没有数据");
        }
        Log.info("数据量："+datas.size());
        for (int i = 0; i < datas.size(); i++) {
            Log.info(datas.getJSONObject(i).toString());
        }

    }


}
