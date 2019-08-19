package dafagame.testCase.cms.tenant.transactionManage.renGongCunKuan;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.timeUtils.TimeUtil;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 人工存款记录
 * 人工提出记录
 *
 */
public class ManualRecordList {

    private static String manualRecordList = "/v1/transaction/manualRecordList";

    @Test(description = "人工存款记录")
    public void test001() {
        String url = UrlBuilder.custom()
                .url(manualRecordList)
                .addBuilder("userName")
                .addBuilder("recordCode")
                //.addBuilder("startTime","2019-08-09 00:00:00")
                //.addBuilder("endTime","2019-08-09 00:00:00")
                .addBuilder("startTime", TimeUtil.getLCTime(0))
                .addBuilder("endTime", TimeUtil.getLCTime(1))
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .addBuilder("inOutFlag", "1")//1存款，0取出
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
        JSONArray rows = jsonResult.getJSONObject("data").getJSONArray("rows");

        System.out.println("数据条数:" + rows.size());
        for (int i = 0; i < rows.size(); i++) {
            System.out.println(rows.getJSONObject(i).toString());
        }
    }



    @Test(description = "人工提出记录")
    public void test002() {
        String url = UrlBuilder.custom()
                .url(manualRecordList)
                .addBuilder("userName")
                .addBuilder("recordCode")
                .addBuilder("startTime","2019-08-01 00:00:00")
                //.addBuilder("endTime","2019-08-09 00:00:00")
                //.addBuilder("startTime", TimeUtil.getLCTime(0))
                .addBuilder("endTime", TimeUtil.getLCTime(1))
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .addBuilder("inOutFlag", "0")//1存款，0取出
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
        JSONArray rows = jsonResult.getJSONObject("data").getJSONArray("rows");

        System.out.println("数据条数:" + rows.size());
        for (int i = 0; i < rows.size(); i++) {
            System.out.println(rows.getJSONObject(i).toString());
        }
    }
}
