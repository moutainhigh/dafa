package pers.testProductBug;

import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;

public class GetServerTimeMillisecondXZ {

    private static String getServerTimeMillisecond = "http://www.dfcdn38.com/v1/betting/getServerTimeMillisecond";

    @Test(description = "测试")
    public static void test01() {
        for (int i = 0; i < 50; i++) {
            String s= DafaRequest.get(getServerTimeMillisecond);
            System.out.println(s);
        }

    }
}
