package pers.dafacloud.testCase.user;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pers.dafacloud.enums.Environment;
import pers.dafacloud.httpUtils.Request;
import pers.dafacloud.report.ZTestReport;
import org.testng.Reporter;

@Listeners({ ZTestReport.class })
public class UserInfo {
    static Environment environment = Environment.DEFAULT;

    @BeforeClass
    public void beforeClass() {

    }

    @Test(priority = 1,description ="用户信息")
    public void testUsersInfo(){
        String url = environment.url+"/v1/users/info";
        String r = Request.doGet(url);
        Reporter.log(r);
        Assert.assertEquals(true,r.contains("成功"),"获取info失败");
    }
}
