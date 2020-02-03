package dafagame.testCase.cms.tenant.transactionManage.transactionRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.enums.Path;


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

    }
}
