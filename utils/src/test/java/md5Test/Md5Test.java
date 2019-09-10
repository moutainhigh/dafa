package md5Test;

import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;

public class Md5Test {

    @Test(description = "测试")
    public static void test01() {
        System.out.println(DigestUtils.md5Hex("duke123"));
    }

    @Test(description = "测试")
    public static void test02() {
        System.out.println(DigestUtils.md5Hex("dafai0000"+DigestUtils.md5Hex("123456")));
        //ff5cb39212aa72243fa6d89914ee26bc
    }


}
