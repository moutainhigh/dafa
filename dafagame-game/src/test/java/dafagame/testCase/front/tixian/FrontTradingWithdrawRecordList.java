package dafagame.testCase.front.tixian;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 提现 > 提现记录
 */
//@Listeners(ZTestReport.class)
public class FrontTradingWithdrawRecordList {
    private static String frontTradingWithdrawRecordList = "/v1/transaction/frontTradingWithdrawRecordList";

    @Test(description = "提现>提现记录")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(frontTradingWithdrawRecordList)
                .addBuilder("state","-1")
                .addBuilder("dateType","sevenday")
                .addBuilder("pageSize","7")
                .addBuilder("pageNum","1")
                .addBuilder("self","true")
                .fullUrl();
        String result = DafaRequest.get(0,url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
        JSONArray rows = jsonResult.getJSONObject("data").getJSONArray("rows");
        System.out.println("数据量："+rows.size());
        if(rows.size()!=0){
            for (int i = 0; i < rows.size(); i++) {
                System.out.println(rows.getJSONObject(i));
            }
        }
    }
}
