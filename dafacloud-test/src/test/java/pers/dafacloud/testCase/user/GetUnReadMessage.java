package pers.dafacloud.testCase.user;

import org.testng.Assert;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import org.testng.Reporter;

public class GetUnReadMessage {

    Path path = Path.unReadMessage;

    @Test(priority = 1, description = "获取用户未读信息")
    public void testGetUnreadMessage() {

        String url =path.value;
        System.out.println(url);
        String s = Request.doGet(url);
        Reporter.log(s);
        Assert.assertEquals(true, s.contains("获取成功"), "获取用户未读消息失败");
        System.out.println("获取用户未读信息成功");
    }
}
