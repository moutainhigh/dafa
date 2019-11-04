package pers.testProductBug;


import org.testng.annotations.Test;
import pers.utils.dafaCloudLotteryUtils.Register;

/**
 * ip注册上限
 */
public class RegisterIPLimit {

    @Test(description = "测试环境,测试账号上限50个")
    public static void test01() {
        String url = "http://caishen02.com";
        String username = "testCookie";
        String inviteCode = "15940420";//测试环境大发站，测试账号邀请码
        for (int i = 0; i < 60; i++) {
            Register.registerMeth(url, username + String.format("%05d", i), inviteCode);
        }
    }


    @Test(description = "测试环境，正式账号50个")
    public static void test02() {
        String url = "http://caishen02.com";
        String username = "abcb";
        String inviteCode = "72562999";//测试环境大发站，测试账号邀请码
        for (int i = 30; i < 60; i++) {
            Register.registerMeth(url, username + String.format("%05d", i), inviteCode);
        }
    }

}
