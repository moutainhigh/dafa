package pers.dafacloud.testCase.user;

import org.testng.Assert;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import org.testng.Reporter;

public class GetBankCardListFront {
    static Path path = Path.getBankCardListFront;

    @Test(priority = 1, description = "获取用户银行卡")
    public void testGetBankCard() {
        String url = path.value;
        System.out.println(url);
        String s = Request.doGet(url);
        Reporter.log(s);
        Assert.assertEquals(true, s.contains("获取成功"), "获取银行卡失败");
        System.out.println("获取用户银行卡成功");
    }
}
