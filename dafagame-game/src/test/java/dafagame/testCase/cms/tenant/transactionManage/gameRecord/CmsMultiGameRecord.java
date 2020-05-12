package dafagame.testCase.cms.tenant.transactionManage.gameRecord;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.urlUtils.UrlBuilder;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 交易管理 >游戏记录查询 > 多人游戏记录
 */
public class CmsMultiGameRecord {

    private static String path = "/v1/game/cmsMultiGameRecord";
    private static String cmsMultiGameRecordInfo = "/v1/game/cmsMultiGameRecordInfo";

    @Test(description = "查询所有")
    public static void test01() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(path)
                        .addBuilder("tenantCode.txt", "10")//厅主编码
                        .addBuilder("userName", "")
                        .addBuilder("gameCode", "") //101 百人牛牛，102
                        .addBuilder("gameState", "") // 结果 1输，2赢，0游戏中，3系统撤单
                        .addBuilder("roundType", "") //场次 101 4倍场,102 10倍场
                        .addBuilder("roomNumber", "") //房号
                        .addBuilder("inning", "") //局号
                        .addBuilder("recordCode", "") //流水号
                        .addBuilder("startTime", "2019-07-01")
                        .addBuilder("endTime", "2019-07-18")
                        .addBuilder("pageSize", "20")
                        .addBuilder("pageNum", "1")
                        .fullUrl());
        //System.out.println(result);
        JSONArray cmsMultiGameRecordList = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("cmsMultiGameRecordList");
        System.out.println("数据量" + cmsMultiGameRecordList.size());
        for (Object ob : cmsMultiGameRecordList) {
            System.out.println(ob);
            JSONObject obb = (JSONObject) ob;
            String cmsMultiGameRecordInfoResult = DafaRequest.get(1,UrlBuilder.custom().url(cmsMultiGameRecordInfo).addBuilder("id", obb.getString("id")).fullUrl());
            System.out.println(cmsMultiGameRecordInfoResult);
        }
    }

    @Test(description = "roundType场次测试")
    public static void test02() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(path)
                        .addBuilder("tenantCode.txt", "10")//厅主编码
                        .addBuilder("userName", "")
                        .addBuilder("gameCode", "101") //101 百人牛牛，
                        .addBuilder("gameState", "")
                        .addBuilder("roundType", "102")
                        .addBuilder("roomNumber", "")
                        .addBuilder("inning", "") //局号
                        .addBuilder("recordCode", "") //流水号
                        .addBuilder("startTime", "2019-07-01")
                        .addBuilder("endTime", "2019-07-18")
                        .addBuilder("pageSize", "20")
                        .addBuilder("pageNum", "1")
                        .fullUrl());
        System.out.println(result);
        JSONArray cmsMultiGameRecordList = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("cmsMultiGameRecordList");
        System.out.println("数据量" + cmsMultiGameRecordList.size());
        for (Object ob : cmsMultiGameRecordList) {
            System.out.println(ob);
        }
    }


    @Test(description = "gameCode游戏编码")
    public static void test03() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(path)
                        .addBuilder("tenantCode.txt", "10")//厅主编码
                        .addBuilder("userName", "")
                        .addBuilder("gameCode", "101") //101 百人牛牛，
                        .addBuilder("gameState", "")
                        .addBuilder("roundType", "102")
                        .addBuilder("roomNumber", "")
                        .addBuilder("inning", "") //局号
                        .addBuilder("recordCode", "") //流水号
                        .addBuilder("startTime", "2019-07-01")
                        .addBuilder("endTime", "2019-07-18")
                        .addBuilder("pageSize", "20")
                        .addBuilder("pageNum", "1")
                        .fullUrl());
        System.out.println(result);
        JSONArray cmsMultiGameRecordList = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("cmsMultiGameRecordList");
        System.out.println("数据量" + cmsMultiGameRecordList.size());
        for (Object ob : cmsMultiGameRecordList) {
            System.out.println(ob);
        }
    }

    @Test(description = "gameState结果状态")
    public static void test04() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(path)
                        .addBuilder("tenantCode.txt", "10")//厅主编码
                        .addBuilder("userName", "")
                        .addBuilder("gameCode", "101") //101 百人牛牛，
                        .addBuilder("gameState", "1")
                        .addBuilder("roundType", "102")
                        .addBuilder("roomNumber", "")
                        .addBuilder("inning", "") //局号
                        .addBuilder("recordCode", "") //流水号
                        .addBuilder("startTime", "2019-07-01")
                        .addBuilder("endTime", "2019-07-18")
                        .addBuilder("pageSize", "20")
                        .addBuilder("pageNum", "1")
                        .fullUrl());
        System.out.println(result);
        JSONArray cmsMultiGameRecordList = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("cmsMultiGameRecordList");
        System.out.println("数据量" + cmsMultiGameRecordList.size());
        for (Object ob : cmsMultiGameRecordList) {
            System.out.println(ob);
        }
    }

    @Test(description = "inning局号")
    public static void test05() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(path)
                        .addBuilder("tenantCode.txt", "10")//厅主编码
                        .addBuilder("userName", "")
                        .addBuilder("gameCode", "101") //101 百人牛牛，
                        .addBuilder("gameState", "1")
                        .addBuilder("roundType", "102")
                        .addBuilder("roomNumber", "")
                        .addBuilder("inning", "07170114") //局号
                        .addBuilder("recordCode", "") //流水号
                        .addBuilder("startTime", "2019-07-01")
                        .addBuilder("endTime", "2019-07-18")
                        .addBuilder("pageSize", "20")
                        .addBuilder("pageNum", "1")
                        .fullUrl());
        System.out.println(result);
        JSONArray cmsMultiGameRecordList = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("cmsMultiGameRecordList");
        System.out.println("数据量" + cmsMultiGameRecordList.size());
        for (Object ob : cmsMultiGameRecordList) {
            System.out.println(ob);
        }
    }

    @Test(description = "recordCode单号")
    public static void test06() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(path)
                        .addBuilder("tenantCode.txt", "10")//厅主编码
                        .addBuilder("userName", "")
                        .addBuilder("gameCode", "") //101 百人牛牛，
                        .addBuilder("gameState", "")
                        .addBuilder("roundType", "")
                        .addBuilder("roomNumber", "")
                        .addBuilder("inning", "") //局号
                        .addBuilder("recordCode", "1010717100010228") //流水号
                        .addBuilder("startTime", "2019-07-01")
                        .addBuilder("endTime", "2019-07-18")
                        .addBuilder("pageSize", "20")
                        .addBuilder("pageNum", "1")
                        .fullUrl());
        System.out.println(result);
        JSONArray cmsMultiGameRecordList = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("cmsMultiGameRecordList");
        System.out.println("数据量" + cmsMultiGameRecordList.size());
        for (Object ob : cmsMultiGameRecordList) {
            System.out.println(ob);
        }
    }

}
