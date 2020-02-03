package dafagame.testCase.cms.tenant.transactionManage.transactionRecord;

import dafagame.testCase.cms.tenant.transactionManage.tixianManage.WithdrawRecordList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.enums.Path;

/**
 * 交易记录查询 > 提现记录（后台GET）
 * */
public class TradingWithdrawRecordList {

    private final static Logger Log = LoggerFactory.getLogger(WithdrawRecordList.class);
    static Path path = Path.tradingWithdrawRecordList;

    @Test(priority = 1,description = "所有数据")
    public void test001(){
        String url = path.value+"?userName=&recordCode=&state=-1&startTime=2019-05-24&endTime=2019-05-25&pageNum=1&pageSize=20";
        //Reporter.log(result);
    }
}
