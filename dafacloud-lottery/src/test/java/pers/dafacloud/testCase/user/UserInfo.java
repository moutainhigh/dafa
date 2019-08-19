package pers.dafacloud.testCase.user;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import pers.dafacloud.utils.report.ZTestReport;
import org.testng.Reporter;

@Listeners({ ZTestReport.class })
public class UserInfo {

    Path path = Path.rebate;

    @BeforeClass
    public void beforeClass() {

    }

    @Test(priority = 1,description ="获取用户信息")
    public void testUsersInfo(){

        String url = path.value;
        System.out.println(url);
        String r = Request.doGet(url);
         /*
        JSONObject obj = JSONObject.fromObject(r);
        System.out.println(obj.getString("msg"));
        JSONObject objSon = obj.getJSONObject("data");
        System.out.println("最后登陆时间: "+objSon.getString("lastLoginIp"));
        System.out.println("最后登陆IP: "+objSon.getString("lastLoginTime"));
        */
        Reporter.log(r);
        Assert.assertEquals(true,r.contains("获取成功"),"获取info失败");
        System.out.println("获取用户信息成功");
    }
}
