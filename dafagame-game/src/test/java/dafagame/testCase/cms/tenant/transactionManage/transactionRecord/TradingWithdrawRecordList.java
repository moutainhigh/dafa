package dafagame.testCase.cms.tenant.transactionManage.transactionRecord;

import dafagame.testCase.cms.tenant.transactionManage.tixianManage.WithdrawRecordList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;

/**
 * 交易记录查询 > 提现记录（后台GET）
 * */
public class TradingWithdrawRecordList {

    private final static Logger Log = LoggerFactory.getLogger(WithdrawRecordList.class);
    static Path path = Path.tradingWithdrawRecordList;

    @Test(priority = 1,description = "所有数据")
    public void test001(){
        String url = path.value+"?userName=&recordCode=&state=-1&startTime=2019-05-24&endTime=2019-05-25&pageNum=1&pageSize=20";

        String result = Request.doGet(url);
        Log.info(result);
        JSONObject jo = JSONObject.fromObject(result);
        //失败
        if(!"1".equals(jo.getString("code"))){
            return;
        }
        JSONArray ja = jo.getJSONObject("data").getJSONArray("rows");
        //没有数据
        if(ja.size()==0){
            return;
        }
        //有数据
        System.out.println("数据条数:"+ja.size());
        for (int i = 0; i < ja.size(); i++) {
            System.out.println(ja.getJSONObject(i).toString());
        }
        Reporter.log(result);
    }
}
