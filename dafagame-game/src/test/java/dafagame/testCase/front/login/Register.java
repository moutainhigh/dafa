package dafagame.testCase.front.login;

import org.testng.annotations.Test;
import dafagame.common.RegisterDafaGame;


public class Register {

    @Test(description = "批量注册")
    public static void test01() {
        String url = "http://duke.dafagame-testCookie.com:86/";
        String phone = "1361234";
        String inviteCode = "63508529"; //
        for (int i = 0; i < 1; i++) {
            RegisterDafaGame.registerMeth(url, String.format("%s%s", phone, String.format("%04d", i)), inviteCode);
        }
    }

    @Test(description = "注册，dukea站")
    public static void test02() {
        String url = "http://dafagame-testCookie.com:86";
        String phone = "13612345602";
        String inviteCode = "2643153"; //
        RegisterDafaGame.registerMeth(url, phone, inviteCode);
    }

    @Test(description = "注册，cindy站")
    public static void test03() {
        String url = "http://duke.dafagame-testCookie.com:86";
        String phone = "13612345601";
        String inviteCode = "5076637"; //
        RegisterDafaGame.registerMeth(url, phone, inviteCode);
    }

}
