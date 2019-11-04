package dafagame.testCase.front.record.transactionRecord;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.urlUtils.UrlBuilder;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 记录 > 交易记录 > 充值记录查询
 */

public class FrontSummaryPaymentRecordList {

    private static String path = "/v1/transaction/frontSummaryPaymentRecordList";

    @Test(description = "充值记录")
    public static void test01() {
        String result = DafaRequest.get(0,
                UrlBuilder.custom()
                        .url(path)
                        .addBuilder("pageSize", "10")
                        .addBuilder("pageNum", "1")
                        .addBuilder("state","-1") //-1是查全部
                        .addBuilder("dateType","sevenday") //最近7天
                        .addBuilder("self","true") //查自身
                        .fullUrl());
        //RecordListFomat.fomatHandle(result);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
        JSONArray rows = jsonResult.getJSONObject("data").getJSONArray("rows");
        if(rows.size()==0){
            return;
        }
        System.out.println("数据量："+rows.size());
        for (int i = 0; i < rows.size(); i++) {
            JSONObject row = rows.getJSONObject(i);
            System.out.println(row);
        }
    }
}
