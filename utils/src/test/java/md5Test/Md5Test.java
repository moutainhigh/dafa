package md5Test;

import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;

public class Md5Test {

    @Test(description = "测试")
    public static void test01() {
        System.out.println(DigestUtils.md5Hex("duke123"));
    }


}
