package pers.dafacloud.testCaseBet;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pers.dafacloud.enums.Environment;
import pers.dafacloud.httpUtils.Request;
import pers.dafacloud.report.ZTestReport;

@Listeners({ ZTestReport.class })
public class DafaK3 {
    static Environment environment = Environment.DEFAULT;
    static  String url = environment.url+"/v1/users/info";

    @BeforeClass
    public void beforeClass() {

    }

    @Test(priority = 1,description ="彩种1")
    public void testUsersInfo(){
        String r = Request.doPost(url,"");
        Reporter.log(r);
        Assert.assertEquals(true,r.contains("成功"),"获取info失败");
    }
}
