package dafagame.testCase.cms.tenant.transactionManage.congZhiManage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;

/**
 * 第四方充值外部调用接口
 *
 * */
public class RechargeFourthPartyPaymentRecord {

    static Path path = Path.rechargeFourthPartyPaymentRecord;

    @Test(description = "第四方充值外部调用接口")
    public void test001(){
        String body = "tenantCode=test&merchantNumber=481080ef11b44d3bbb392b2df8263641&userName=88467689&amount=1001&orderId=123456789003&sourceName=web&remark=duke第四方充值外部调用接口123ß";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
    }
}
