package pers.dafacloud.testCase.user;

import org.testng.Assert;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Param;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import org.testng.Reporter;

public class GetSampleInfo {
    Path path = Path.card;
    Param param = Param.userId;

    @Test(priority = 1, description = "获取用户(ID=50000433)简易信息")
    public void testGetSampleInfo() {

        String url =path.value + param.params;
        System.out.println(url);
        String s = Request.doGet(url);
        Reporter.log(s);
        Assert.assertEquals(true, s.contains("获取成功"), "获取用户简易信息失败");
        System.out.println("获取用户(ID=50000433)简易信息成功");
    }
}
