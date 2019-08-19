package dafagame.testCase.cms.tenant.transactionManage.congZhiManage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 第三方快捷接口强制入款（后台POST）
 */
public class UpdateThirdPartyPaymentState {

    private static String updateSummaryPaymentRecordState = "/v1/transaction/updateSummaryPaymentRecordState";
    private static String paymentRecordList = "/v1/transaction/paymentRecordList";
    private static JSONArray rows;

    @BeforeClass
    public void beforeClass() {
        //获取数据
        String url = UrlBuilder.custom()
                .url(paymentRecordList)
                .addBuilder("userName", "")
                .addBuilder("dictionId", "")
                .addBuilder("accountName", "")
                .addBuilder("state", "-1")
                .addBuilder("grades", "")
                .addBuilder("recordCode", "")
                .addBuilder("rechargeType", "1") //1第三方
                .addBuilder("startAmount", "")
                .addBuilder("endAmount", "")
                .addBuilder("startTime", "2019-08-01 00:00:00")
                .addBuilder("endTime", "2019-08-07 00:00:00")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        String result = DafaRequest.get(1,url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        rows = jsonResult.getJSONObject("data").getJSONArray("rows");
        if (rows.size() == 0) {
            AssertUtil.assertNull(false, "没有充值数据");
            return;
        }
        System.out.println("数据条数:" + rows.size());
    }

    @Test(priority = 1, description = "第三方强制确认")
    public void test001() {
        int count = 0;
        for (int i = 0; i < rows.size(); i++) {
            JSONObject row = rows.getJSONObject(i);
            System.out.println(row);
            if (row.getInt("state") == 0) {//state : 0未确认，1（确认），2（2小时未回复取消）
                //operateType: 0取消确认，1（确认），3手动取消
                String body = String.format("operateType=%s&id=%s", 2, row.getInt("id"));//"operateType=1&id=101751373";
                String result0 = DafaRequest.post(1,updateSummaryPaymentRecordState, body);
                JSONObject jsonResult0 = JSONObject.fromObject(result0);
                AssertUtil.assertCode(jsonResult0.getInt("code") == 1, result0);
                count++;
            }
        }
        if (count == 0) {
            AssertUtil.assertNull(false, "没有待强制确认第三方充值数据(state=0)的充值数据");
        }
    }
}
