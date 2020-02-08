package dafagame.testCase.cms.tenant.transactionManage.tixianManage;

import com.google.gson.JsonObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.jsonUtils.JsonOfObject;
import pers.utils.jsonUtils.Response;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 提现管理 > 提现订单管理"查询"和"导出"接口（后台GET）
 */
public class WithdrawRecordList {


    private static String withdrawRecordList = "/v1/transaction/withdrawRecordList";
    public static void main(String[] args) {
        getWithdrawRecordList();
    }

    /**
     * 获取未锁定，未确认的提现数据id
     */
    public static JSONArray getWithdrawRecordList() {
        String result = DafaRequest.get(1,UrlBuilder.custom()
                .url(withdrawRecordList)
                .addBuilder("userName", "")
                .addBuilder("grades", "")
                .addBuilder("state", "-1")// -1全部 0	未确定，1	确定，2	取消  12小时未回复取消，3	取消
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
                .addBuilder("startTime", "2019-07-01")
                .addBuilder("endTime", "2019-07-24")
                .addBuilder("startAmount", "")
                .addBuilder("endAmount", "")
                .fullUrl());
        Response response = JsonOfObject.jsonToObj(Response.class, result);
        AssertUtil.assertCode(response.isSuccess(), result);
        JSONArray ja = response.getData(JSONObject.class).getJSONArray("rows");
        //没有数据
        if (ja.size() == 0) {
            Log.info("未绑定银行卡");
            return null;
        }
        //有数据
        Log.info(String.format("数据条数:%s", ja.size()));
        return ja;
    }
//
//    @pers.utils.daoUtils.UnderlineHump(priority = 1, description = "所有用户")
//    public void test002() {
//        String url = withdrawRecordList + "?userName=&state=0&startTime=2019-05-24&endTime=2019-05-25&pageNum=1&pageSize=20";
//        System.out.println(url);
//        String result = Request.doGet(url);
//        Log.info(result);
//        JSONObject jo = JSONObject.fromObject(result);
//        //失败
//        if (!"1".equals(jo.getString("code"))) {
//            return;
//        }
//        JSONArray ja = jo.getJSONObject("data").getJSONArray("rows");
//        //没有数据
//        if (ja.size() == 0) {
//            return;
//        }
//        //有数据
//        System.out.println("数据条数:" + ja.size());
//        for (int i = 0; i < ja.size(); i++) {
//            System.out.println(ja.getJSONObject(i).toString());
//        }
//        Reporter.log(result);
//        //Assert.assertEquals(true,s.contains("获取成功"),"失败");
//    }
//
//    @pers.utils.daoUtils.UnderlineHump(priority = 1, description = "所有状态")
//    public void test003() {
//        String url = withdrawRecordList + "?userName=88467689&state=0&startTime=2019-05-24&endTime=2019-05-25&pageNum=1&pageSize=20";
//        System.out.println(url);
//        String result = Request.doGet(url);
//        Log.info(result);
//        JSONObject jo = JSONObject.fromObject(result);
//        //失败
//        if (!"1".equals(jo.getString("code"))) {
//            return;
//        }
//        JSONArray ja = jo.getJSONObject("data").getJSONArray("rows");
//        //没有数据
//        if (ja.size() == 0) {
//            return;
//        }
//        //有数据
//        System.out.println("数据条数:" + ja.size());
//        for (int i = 0; i < ja.size(); i++) {
//            System.out.println(ja.getJSONObject(i).toString());
//        }
//        Reporter.log(result);
//        //Assert.assertEquals(true,s.contains("获取成功"),"失败");
//    }
//
//    @pers.utils.daoUtils.UnderlineHump(priority = 1, description = "所有状态")
//    public void test004() {
//        String url = withdrawRecordList + "?userName=&state=-1&startTime=2019-05-23&endTime=2019-05-25&pageNum=1&pageSize=20";
//        System.out.println(url);
//        String result = Request.doGet(url);
//        Log.info(result);
//        JSONObject jo = JSONObject.fromObject(result);
//        //失败
//        if (!"1".equals(jo.getString("code"))) {
//            return;
//        }
//        JSONArray ja = jo.getJSONObject("data").getJSONArray("rows");
//        //没有数据
//        if (ja.size() == 0) {
//            return;
//        }
//        //有数据
//        System.out.println("数据条数:" + ja.size());
//        for (int i = 0; i < ja.size(); i++) {
//            System.out.println(ja.getJSONObject(i).toString());
//        }
//        Reporter.log(result);
//        //Assert.assertEquals(true,s.contains("获取成功"),"失败");
//    }
//
//    @pers.utils.daoUtils.UnderlineHump(priority = 1, description = "WQR(0,未确认)")
//    public void test005() {
//        String url = withdrawRecordList + "?userName=&state=0&startTime=2019-05-23&endTime=2019-05-25&pageNum=1&pageSize=20";
//        System.out.println(url);
//        String result = Request.doGet(url);
//        Log.info(result);
//        JSONObject jo = JSONObject.fromObject(result);
//        //失败
//        if (!"1".equals(jo.getString("code"))) {
//            return;
//        }
//        JSONArray ja = jo.getJSONObject("data").getJSONArray("rows");
//        //没有数据
//        if (ja.size() == 0) {
//            return;
//        }
//        //有数据
//        System.out.println("数据条数:" + ja.size());
//        for (int i = 0; i < ja.size(); i++) {
//            System.out.println(ja.getJSONObject(i).toString());
//        }
//        Reporter.log(result);
//        //Assert.assertEquals(true,s.contains("获取成功"),"失败");
//    }
//
//
//    @pers.utils.daoUtils.UnderlineHump(priority = 1, description = "TXCG(1,提现成功)")
//    public void test006() {
//        String url = withdrawRecordList + "?userName=&state=1&startTime=2019-05-23&endTime=2019-05-25&pageNum=1&pageSize=20";
//        System.out.println(url);
//        String result = Request.doGet(url);
//        Log.info(result);
//        JSONObject jo = JSONObject.fromObject(result);
//        //失败
//        if (!"1".equals(jo.getString("code"))) {
//            return;
//        }
//        JSONArray ja = jo.getJSONObject("data").getJSONArray("rows");
//        //没有数据
//        if (ja.size() == 0) {
//            return;
//        }
//        //有数据
//        System.out.println("数据条数:" + ja.size());
//        for (int i = 0; i < ja.size(); i++) {
//            System.out.println(ja.getJSONObject(i).toString());
//        }
//        Reporter.log(result);
//        //Assert.assertEquals(true,s.contains("获取成功"),"失败");
//    }
//
//    @pers.utils.daoUtils.UnderlineHump(priority = 1, description = "TXTHCG(2,提现退回成功)")
//    public void test007() {
//        String url = withdrawRecordList + "?userName=&state=1&startTime=2019-05-23&endTime=2019-05-25&pageNum=1&pageSize=20";
//        System.out.println(url);
//        String result = Request.doGet(url);
//        Log.info(result);
//        JSONObject jo = JSONObject.fromObject(result);
//        //失败
//        if (!"1".equals(jo.getString("code"))) {
//            return;
//        }
//        JSONArray ja = jo.getJSONObject("data").getJSONArray("rows");
//        //没有数据
//        if (ja.size() == 0) {
//            return;
//        }
//        //有数据
//        System.out.println("数据条数:" + ja.size());
//        for (int i = 0; i < ja.size(); i++) {
//            System.out.println(ja.getJSONObject(i).toString());
//        }
//        Reporter.log(result);
//        //Assert.assertEquals(true,s.contains("获取成功"),"失败");
//    }
//
//    @pers.utils.daoUtils.UnderlineHump(priority = 1, description = "JJCK(3,拒绝出款)")
//    public void test008() {
//        String url = withdrawRecordList + "?userName=&state=3&startTime=2019-05-23&endTime=2019-05-25&pageNum=1&pageSize=20";
//        System.out.println(url);
//        String result = Request.doGet(url);
//        Log.info(result);
//        JSONObject jo = JSONObject.fromObject(result);
//        //失败
//        if (!"1".equals(jo.getString("code"))) {
//            return;
//        }
//        JSONArray ja = jo.getJSONObject("data").getJSONArray("rows");
//        //没有数据
//        if (ja.size() == 0) {
//            return;
//        }
//        //有数据
//        System.out.println("数据条数:" + ja.size());
//        for (int i = 0; i < ja.size(); i++) {
//            System.out.println(ja.getJSONObject(i).toString());
//        }
//        Reporter.log(result);
//        //Assert.assertEquals(true,s.contains("获取成功"),"失败");
//    }
}
