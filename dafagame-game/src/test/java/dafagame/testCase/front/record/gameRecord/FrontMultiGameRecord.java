package dafagame.testCase.front.record.gameRecord;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.urlUtils.UrlBuilder;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 记录 > 游戏记录 > 多人游戏记录
 */
public class FrontMultiGameRecord {

    private static String path = "/v1/game/frontMultiGameRecord";
    private static String info = "/v1/game/frontMultiGameRecordInfo";

    @Test(description = "多人游戏记录")
    public static void test01() {
        String result = DafaRequest.get(0,
                UrlBuilder.custom()
                .url(path)
                .addBuilder("pageSize", "10")
                .addBuilder("pageNum", "1")
                .fullUrl());
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
        JSONArray list = jsonResult.getJSONObject("data").getJSONArray("list");
        if (list.size() == 0) {
            AssertUtil.assertNull(false, "没有多人游戏记录");
        }
        System.out.println("数据量："+list.size());
        for (int i = 0; i < list.size(); i++) {
            JSONObject row = list.getJSONObject(i);
            System.out.println(row);

            String result0 = DafaRequest.get(0,
                    UrlBuilder.custom()
                            .url(info)
                            .addBuilder("orderId", row.getString("id"))
                            .addBuilder("userId", "0")
                            .fullUrl());
            JSONObject jsonResult0 = JSONObject.fromObject(result0);
            AssertUtil.assertCode(jsonResult0.getInt("code")==1,result0);
        }
    }

    /*@pers.utils.daoUtils.UnderlineHump(description = "多人游戏记录详情")
    public static void test02() {
        String result = DafaRequest.get(0,
                UrlBuilder.custom()
                        .url(info)
                        .addBuilder("orderId", "18")
                        .addBuilder("userId", "0")
                        .fullUrl());
        RecordListFomat.fomatHandle(result);
        //System.out.println(result);
    }*/

}
