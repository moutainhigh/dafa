package dafagame.testCase.cms.tenant.tenantManager.congZhiManage.bankTransfer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

public class UpdateBankTransferSort {

    private static String updateBankTransferSort = "/v1/transaction/updateBankTransferSort";
    private static String bankTransferList = "/v1/transaction/bankTransferList";

    @Test(description = "银行转账充值排序和展示等级")
    public static void test01() {
        JsonArrayBuilder jsonArrayBuilder = JsonArrayBuilder.custom();
        //1.获取
        String result = DafaRequest.get(1, bankTransferList);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray ja = jsonResult.getJSONArray("data");
        /*for (int i = 0; i < ja.size(); i++) {
            jsonArrayBuilder.addObject(ja.get(i).toString());
        }*/
        JSONArray jsonArray = jsonArrayBuilder.addObject(
                JsonObjectBuilder.custom()
                        .put("id", ja.getJSONObject(1).getString("id")) //取第一条数据排序
                        .put("gradeList", "-1,1,2,3,4,5,6,7,8,9")
                        .put("sort", "1")
                        .bulid()).bulid();
        UrlBuilder.custom().fullBody();
        //System.out.println(jsonArray.toString());

        String body0 = UrlBuilder.custom()
                .addBuilder("data", jsonArray.toString())
                .fullBody();
        //System.out.println(body0);

        String result0 = DafaRequest.post(1, updateBankTransferSort, body0);
        JSONObject jsonResult0 = JSONObject.fromObject(result0);
        AssertUtil.assertCode(jsonResult0.getInt("code") == 1, result0);
    }


    @Test(description = "测试")
    public static void test06() {


    }

}
