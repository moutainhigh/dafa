package dafagame.testCase.cms.tenant.transactionManage.transactionRecord;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;

/**
 * 交易记录查询 > 充值记录查询（后台GET）
 * */
public class SummaryPaymentRecordList {

    private final static Logger Log = LoggerFactory.getLogger(SummaryPaymentRecordList.class);
    static Path path = Path.summaryPaymentRecordList;

    @Test(priority = 1,description = "查询所有")
    public void test001(){
        String url = path.value+"?userName=&state=-1&recordCode" +
                "&dictionId=&grades=1&startTime=2019-05-26&endTime=2019-05-27&pageNum=1&pageSize=20";
        String result = Request.doGet(url);
        Log.info(result);
        JSONObject jo = JSONObject.fromObject(result);
        if(!"1".equals(jo.getString("code"))){
            return;
        }
        JSONArray ja = jo.getJSONObject("data").getJSONArray("rows");
        if(ja.size()==0){
            return;
        }
        System.out.println("数据条数:"+ja.size());
        for (int i = 0; i < ja.size(); i++) {
            System.out.println(ja.getJSONObject(i).toString());

        }
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }
}
