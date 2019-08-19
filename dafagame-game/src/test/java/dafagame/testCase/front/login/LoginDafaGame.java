package dafagame.testCase.front.login;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import dafagame.common.Login;
import pers.dafagame.utils.report.ZTestReport;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.jsonUtils.JsonOfObject;
import pers.utils.jsonUtils.Response;

/**
 * 棋牌游戏 > 前台 > 登陆用例
 */

@Listeners({ ZTestReport.class })
public class LoginDafaGame {

    private static Login loginPage = new Login();

//    @Test(description = "用户名登陆")
//    public static void test01() {
//        String phone = "95231885";
//        String result = loginPage.loginDafaGame(phone);
//        Response response = JsonOfObject.jsonToObj(Response.class,result);
//        AssertUtil.assertCode(response.isSuccess(),result);
//        PropertiesUtil.setProperty("headerSessionId",((JSONObject)response.getData()).getString("sessionId"));
//    }

    @Test(description = "手机号登陆")
    public static void test02() {
        String phone = "13012345671";
        String result = loginPage.loginDafaGame(phone);
        Response response = JsonOfObject.jsonToObj(Response.class,result);
        AssertUtil.assertCode(response.isSuccess(),result);
    }

    @Test(description = "错误账号")
    public static void test03() {
        String phone = "25944982";
        String result = loginPage.loginDafaGame(phone);
        AssertUtil.assertContains(result, "错误");//{"code":-1,"msg":"账号或密码错误","data":1}
    }

    @Test(description = "错误密码")
    public static void test04() {
        String phone = "123456735";
        String result = loginPage.loginDafaGame(phone);
        AssertUtil.assertContains(result, "请输入");//{"code":-1,"msg":"请输入正确的账号或手机号","data":null}
    }

}
