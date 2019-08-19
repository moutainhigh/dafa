package pers.dafacloud.testCase.user;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Environment;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import pers.dafacloud.utils.report.ZTestReport;

@Listeners({ ZTestReport.class })
public class UserOther {
    static Environment environment = Environment.DEFAULT;
    static Path inviteCode = Path.inviteCode;

    @BeforeClass
    public void beforeClass() {

    }

    @Test(priority = 1,description ="用户信息")
    public void testUsersInfo(){
        String url = environment.url+"/v1/users/info";
        String r = Request.doGet(url);
        //Request.doPost("","");
        Reporter.log(r);
        System.out.println(r);
        Assert.assertEquals(true,r.contains("成功"),"获取info失败");
    }

    @Test(priority = 2,description ="用户信息")
    public void testInviteCode(){
        String url = inviteCode.value+"?isAgent=1&pageNum=1&pageSize=10&";
        String r = Request.doGet(url);
        System.out.println(r);
        Reporter.log(r);
        Assert.assertEquals(true,r.contains("成功"),"获取邀请码失败");
    }
}
