package pers.dafacloud.testCase.user;

import org.testng.Assert;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Param;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import org.testng.Reporter;


public class GetAnnouncementContent {

    Path path = Path.getAnnouncementContent;
    Param param = Param.announcementContentId;

    @Test(priority = 1,description = "获取公告内容")
    public void testGetAnnouncementContent(){

        String url = path.value + param.params;
        System.out.println(url);
        String s = Request.doGet(url);
        Reporter.log(s);
        Assert.assertEquals(true,s.contains("获取成功"),"获取公告内容失败");
        System.out.println("获取公告内容成功");
    }
}
