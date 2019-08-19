package pers.dafacloud.testCase.user;

import org.testng.Assert;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Param;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import org.testng.Reporter;

public class GetMessageContent {
    Path path = Path.messageContent;
    Param param = Param.messageContent;

    @Test(priority = 1, description = "获取站内信内容")
    public void testGetMessageContent() {

        String url =path.value + param.params;
        System.out.println(url);
        String s = Request.doGet(url);
        Reporter.log(s);
        Assert.assertEquals(true, s.contains("获取成功"), "获取站内信内容失败");
        System.out.println("获取站内信内容成功");
    }
}
