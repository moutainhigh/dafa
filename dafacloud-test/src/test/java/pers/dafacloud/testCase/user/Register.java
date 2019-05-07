package pers.dafacloud.testCase.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import org.testng.Reporter;
import pers.dafacloud.utils.report.ZTestReport;

@Listeners(ZTestReport.class)
public class Register {

    Path path = Path.register;

    @Test(priority = 1,description = "注册")
    public void testRegister(){
        String url = path.value;
        String userName = "wesley998";
        String password = DigestUtils.md5Hex(userName + DigestUtils.md5Hex("123456"));
        String body = "inviteCode=40924811&userName="+userName+"&password="+password;
        String s = Request.doPost(url,body);
        Reporter.log(s);
        Assert.assertEquals(true,"成功","注册失败");
        System.out.println("注册成功");
    }

}
