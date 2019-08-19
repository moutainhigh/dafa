package dafagame.testCase.cms.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;

public class UpdateBalance {

    private final static Logger Log = LoggerFactory.getLogger(UpdateBalance.class);
    static Path path = Path.saveFrontWithdrawRecord;

    @Test(description = "")
    public void test001() {
        String url = "http://192.168.8.193:7060/v1/balance/updateBalance";
        String body = "{\"userId\":\"55153510\",\"recordCode\":\"8080527123456889\",\"dictionId\":\"302\",\"inOut\":\"1\",\"amount\":\"2\",\"remark\":\"投注\"}";
        String s = Request.doPost(url, body);
        System.out.println(s);
        Reporter.log(s);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }
}
