package pers.dafacloud.testCase.balance;

import org.testng.Assert;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import org.testng.Reporter;

public class QueryBalanceFront {

    Path path = Path.queryBalanceFront;

    @Test(priority = 1,description = "获取当前用户余额")
    public void testQueryBalanceFront(){
        String url = path.value;
        System.out.println(url);
        String s = Request.doGet(url);
        Reporter.log(s);
        Assert.assertEquals(true,s.contains("获取成功"),"获取当前用户余额失败");
        System.out.println("获取当前用户余额成功");

    }
}
