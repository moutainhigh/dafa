package dafagame.testCase.cms.platform.baseManage.tenantUrl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

public class UrlList {
    private static String urlList = "/v1/management/tenant/urlList";

    //url_id/tenantType
    //-- 1	WEB	前台
    //-- 2	CMS	后台
    //-- 3	MAINWEB	官网主域
    @Test(description = "厅主域名管理")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(urlList)
                .addBuilder("tenantType", "")//1	PROXY	直营，2	DIRECT	渠道
                .addBuilder("tenantCode.txt", "")
                .addBuilder("name", "")
                .addBuilder("url", "")
                .addBuilder("urlId", "")//1	WEB	前台,2	CMS	后台,3	MAINWEB	官网主域
                .addBuilder("startDate", "")
                .addBuilder("endDate", "")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray datas = jsonResult.getJSONObject("data").getJSONArray("urlList");
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
