package dafagame.testCase.front.safeBalance;

import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 获取首张锁定的银行卡,
 */
public class GetFirstLockBankCard {

    private static String getFirstLockBankCard = "/v1/users/getFirstLockBankCard";

    @Test(description = "测试")
    public static void test01() {
        String result = DafaRequest.get(0,getFirstLockBankCard);
        System.out.println(result);
    }
}
