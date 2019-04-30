package pers.dafacloud.testCase.user;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pers.dafacloud.page.beting.GetBetRebate;
import pers.dafacloud.page.beting.InitializaIssueEndtime;


public class Login {

    @BeforeClass
    public void beforeClass() {

    }
    @Test(priority = 1,description ="登陆")
    public void test001(){
        pers.dafacloud.page.pageLogin.Login loginPage = new pers.dafacloud.page.pageLogin.Login();
        //使用密码来获取cookie
        String r = loginPage.loginDafaCloud("duke01","123456");
        Reporter.log(r);
        Assert.assertEquals(true,r.contains("成功"),"获取info失败");
    }
    @AfterClass
    public void afterClass() {
        InitializaIssueEndtime.executeInitializa();//初始化期数倒计时
        GetBetRebate.getAllRebate();//初始化返点
    }
}
