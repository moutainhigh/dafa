package dafagame.testCase.cms.tenant.transactionManage.tixianManage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.jsonUtils.JsonOfObject;
import pers.utils.jsonUtils.Response;
import pers.utils.logUtils.Log;
import pers.utils.timeUtils.TimeUtil;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现订单管理 > 确认入款操作（后台POST）
 * <p>
 * type：TXCG(1,"提现成功"),TXTHCG(2,"提现退回(取消)成功"),JJCK(3,"拒绝出款"),
 * JS(-1,"解锁"),SD(-2,"锁定");
 */
public class UpdateWithdrawRecordStatus {

    private static String updateWithdrawRecordStatus = "/v1/transaction/updateWithdrawRecordStatus"; //跟新
    private static String queryWithdrawOrderDetails = "/v1/transaction/queryWithdrawOrderDetails"; //金额明细
    private static String withdrawRecordList = "/v1/transaction/withdrawRecordList";//获取
    private static JSONArray rows;

    @BeforeClass
    public void beforeClass() {
        String result = DafaRequest.get(1, UrlBuilder.custom()
                .url(withdrawRecordList)
                .addBuilder("userName", "")
                .addBuilder("grades", "")
                .addBuilder("state", "-1")// -1全部 0	未确定，1	确定，2	取消  12小时未回复取消，3	取消
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "20")
//                .addBuilder("startTime", "2019-08-05 00:00:00")
//                .addBuilder("endTime", "2019-08-06 00:00:00")
                .addBuilder("startTime", TimeUtil.getLCTime(0))
                .addBuilder("endTime", TimeUtil.getLCTime(1))
                .addBuilder("startAmount", "")
                .addBuilder("endAmount", "")
                .fullUrl());
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        rows = jsonResult.getJSONObject("data").getJSONArray("rows");
        if (rows.size() == 0) {
            AssertUtil.assertNull(false, "没有提现数据");
            return;
        }
        System.out.println("数据条数:" + rows.size());
    }


    @Test(priority = 1, description = "锁定，确认")
    public void test001() {
        //获取数据
        List<Integer> recordList = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            JSONObject data = rows.getJSONObject(i);
            System.out.println(data);
            if ((!Boolean.parseBoolean(data.getString("isLock"))) && data.getInt("state") == 0) {//未锁定且未确定
                //System.out.println(ja.getJSONObject(i).toString());
                recordList.add(data.getInt("id"));
            }
            //查询明细
            String url = UrlBuilder.custom()
                    .url(queryWithdrawOrderDetails)
                    .addBuilder("userId", "1316")
                    .addBuilder("userName", "78938089")
                    //.addBuilder("date","2019-05-29")
                    .fullUrl();
            String result = DafaRequest.get(1, url);
            Response response = JsonOfObject.jsonToObj(Response.class, result);
            AssertUtil.assertCode(response.isSuccess(), result);
        }
        if (recordList.size() == 0) {
            AssertUtil.assertNull(false, "没有未锁定且未确定的数据");
        }
        //1.锁定
        String record = recordList.get(0).toString();
        String body = UrlBuilder.custom()
                .addBuilder("type", "-2")
                .addBuilder("id", record)//取第一条未锁定未确认的数据
                //.addBuilder("remark", "duke测试") //取消，拒绝才需要
                .fullBody();
        String result = DafaRequest.post(1, updateWithdrawRecordStatus, body);
        Response response = JsonOfObject.jsonToObj(Response.class, result);
        AssertUtil.assertCode(response.isSuccess(), result);
        //2.确定或者取消
        String body2 = UrlBuilder.custom()
                .addBuilder("type", "1")
                .addBuilder("id", record)
                .fullBody();
        String result2 = DafaRequest.post(1, updateWithdrawRecordStatus, body2);
        Response response2 = JsonOfObject.jsonToObj(Response.class, result2);
        AssertUtil.assertCode(response2.isSuccess(), result2);
    }

    @Test(priority = 1, description = "锁定，取消")
    public void test001a() {
        //1.锁定
        List<Integer> recordList = WithdrawRecordList.getWithdrawRecordList();
        String record = recordList.get(0).toString();
        String body = UrlBuilder.custom()
                .addBuilder("type", "-2")
                .addBuilder("id", record)
                .fullBody();
        String result = DafaRequest.post(1, updateWithdrawRecordStatus, body);
        Response response = JsonOfObject.jsonToObj(Response.class, result);
        AssertUtil.assertCode(response.isSuccess(), result);
        //2.确定或者取消
        String body2 = UrlBuilder.custom()
                .addBuilder("type", "2")//提现退回
                .addBuilder("id", record)
                .addBuilder("remark", "duke测试取消") //取消，拒绝才需要
                .fullBody();
        String result2 = DafaRequest.post(1, updateWithdrawRecordStatus, body2);
        Response response2 = JsonOfObject.jsonToObj(Response.class, result2);
        AssertUtil.assertCode(response2.isSuccess(), result2);
    }

    @Test(priority = 1, description = "锁定，拒绝")
    public void test001b() {
        //1.锁定
        List<Integer> recordList = WithdrawRecordList.getWithdrawRecordList();
        String record = recordList.get(0).toString();
        String body = UrlBuilder.custom()
                .addBuilder("type", "-2")
                .addBuilder("id", record)//取第一条未锁定未确认的数据
                .fullBody();
        String result = DafaRequest.post(1, updateWithdrawRecordStatus, body);
        Response response = JsonOfObject.jsonToObj(Response.class, result);
        AssertUtil.assertCode(response.isSuccess(), result);
        //2.确定或者取消
        String body2 = UrlBuilder.custom()
                .addBuilder("type", "3")//提现退回
                .addBuilder("id", record)//取第一条未锁定未确认的数据
                .addBuilder("remark", "duke测试拒绝") //取消，拒绝才需要
                .fullBody();
        String result2 = DafaRequest.post(1, updateWithdrawRecordStatus, body2);
        Response response2 = JsonOfObject.jsonToObj(Response.class, result2);
        AssertUtil.assertCode(response2.isSuccess(), result2);
    }

}
