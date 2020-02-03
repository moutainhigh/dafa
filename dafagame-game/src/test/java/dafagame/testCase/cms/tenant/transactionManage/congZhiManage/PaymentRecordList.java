package dafagame.testCase.cms.tenant.transactionManage.congZhiManage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 一般充值和快捷查询接口，第四方充值查询接口（后台GET）
 * 一般（-1全部  0 申请中，1已确定，2 已取消）
 * 快捷（-1全部  0 申请中，1已确定，2 已取消）
 * 第四方（-1全部  1成功，2 失败）
 * <p>
 * rechargeType ： 0一般，1快捷，2第四方查询
 */
public class PaymentRecordList {

    private static String paymentRecordList = "/v1/transaction/paymentRecordList";

    @Test(priority = 1, description = "查询所有")
    public void test001() {
        String url = UrlBuilder.custom()
                .url(paymentRecordList)
                .addBuilder("userName", "")
                .addBuilder("dictionId", "")
                .addBuilder("accountName", "")
                .addBuilder("state", "-1")
                .addBuilder("grades", "")
                .addBuilder("recordCode", "")
                .addBuilder("rechargeType", "0")
                .addBuilder("startAmount", "")
                .addBuilder("endAmount", "")
                .addBuilder("startTime", "2019-08-05 00:00:00")
                .addBuilder("endTime", "2019-08-07 00:00:00")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .fullUrl();
        String result = DafaRequest.get(1,url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray rows = jsonResult.getJSONObject("data").getJSONArray("rows");
        if (rows.size() == 0) {
            return;
        }
        System.out.println("数据条数:" + rows.size());
        for (int i = 0; i < rows.size(); i++) {
            System.out.println(rows.getJSONObject(i).toString());
        }
    }
}
