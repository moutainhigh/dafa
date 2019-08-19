package pers.dafacloud.testCase.user;

import org.testng.Assert;
import org.testng.annotations.Test;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import org.testng.Reporter;

public class UpdateInviteCodeRemark {

    Path path = Path.updateInviteCodeRemark;
    @Test(priority = 1,description = "修改邀请码88938661的备注信息")
    public void testupdateInviteCodeRemark(){

        String url = path.value;
        System.out.println(url);
        String body = "inviteCodeId=587&remark=大佬";
        String s = Request.doPost(url,body);
        Reporter.log(s);
        Assert.assertEquals(true,s.contains("修改成功"),"修改失败");
        System.out.println("邀请码备注修改成功");
    }
}
