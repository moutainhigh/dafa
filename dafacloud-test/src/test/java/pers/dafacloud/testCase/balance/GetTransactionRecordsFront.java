package pers.dafacloud.testCase.balance;

import org.testng.Assert;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Param;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import org.testng.Reporter;

public class GetTransactionRecordsFront {

    Path path = Path.getTransactionRecordsFront;
    Param param = Param.getTransactionRecordsFront;

    @Test(priority = 1,description = "获取当前用户交易记录")
    public void testGetTransactionRecordsFront(){
        String url = path.value + param.params;
        System.out.println(url);
        String s = Request.doGet(url);
        Reporter.log(s);
        Assert.assertEquals(true,s.contains("获取成功"),"获取当前用户交易纪录失败");
        System.out.println("获取当前用户交易记录成功");
    }
}
