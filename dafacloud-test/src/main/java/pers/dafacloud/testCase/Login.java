package pers.dafacloud.testCase;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Login {

    @BeforeClass
    public void beforeClass() {

    }
    @Test(priority = 1,description ="登陆")
    public void test001(){
        pers.dafacloud.pageLogin.Login loginPage = new pers.dafacloud.pageLogin.Login();
        //使用密码来获取cookie
        String r = loginPage.loginDafaCloud("dukea011","123456");
        Reporter.log(r);
        Assert.assertEquals(true,r.contains("成功"),"获取info失败");
    }
}
