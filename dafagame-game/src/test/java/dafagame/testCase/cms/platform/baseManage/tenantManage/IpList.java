package dafagame.testCase.cms.platform.baseManage.tenantManage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

public class IpList {

    private static String ipList = "/v1/management/tenant/ipList";

    @Test(description = "获取站长cms绑定的ip")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(ipList)
                .addBuilder("tenantCode", "duke")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray datas = jsonResult.getJSONObject("data").getJSONArray("ipList");
        if (datas.size() == 0) {
            AssertUtil.assertNull(false, "没有数据");
        }
        Log.info("当前页数据量："+datas.size());
        Log.info("总数据量：" + jsonResult.getJSONObject("data").getInt("total"));
        for (int i = 0; i < datas.size(); i++) {
            Log.info(datas.getJSONObject(i).toString());
        }
    }
}
