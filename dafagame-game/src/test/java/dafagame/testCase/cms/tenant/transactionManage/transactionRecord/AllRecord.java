package dafagame.testCase.cms.tenant.transactionManage.transactionRecord;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.jsonUtils.JsonOfObject;
import pers.utils.jsonUtils.Response;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

public class AllRecord {

    private static String getTransactionRecordsCms = "/v1/balance/getTransactionRecordsCms";

    @Test(description = "测试")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(getTransactionRecordsCms)
                .addBuilder("dictionId", "")
                .addBuilder("userName", "")
                .addBuilder("recordCode", "")
                .addBuilder("startDate", "2019-07-25")
                .addBuilder("endDate", "2019-07-26")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        String result = DafaRequest.get(1, url);
        Response response = JsonOfObject.jsonToObj(Response.class, result);
        AssertUtil.assertCode(response.isSuccess(), result);
        JsonArray ja = response.getData(JsonObject.class).getAsJsonArray("rows");
        if (ja.size() == 0) {
            return;
        }
        Log.info(String.format("数据量：%s", ja.size()));
        for (int i = 0; i < ja.size(); i++) {
            Log.info(ja.get(i).toString());

        }
    }



}
