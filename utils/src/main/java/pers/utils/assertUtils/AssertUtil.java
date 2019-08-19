package pers.utils.assertUtils;

import org.testng.Assert;
import pers.utils.logUtils.Log;

public class AssertUtil {

    /**
     * 断言包含,实际值是否包含预期结果
     */
    public static void assertContains(String ActualValue, String expectValue) {
        if (ActualValue.contains(expectValue)) {
            Log.info(String.format(String.format("断言值成功：", ActualValue)));
        } else {
            Log.info(String.format(String.format("断言值失败,预期值：%s,实际值：%s", expectValue,ActualValue)));
            //throw new RuntimeException(String.format("断言失败：%s", ActualValue));
            Assert.assertTrue(false);
        }
    }

    /**
     * 断言code
     */
    public static void assertCode(boolean isSuccess,String ActualValue) {
        if (isSuccess) {
            Log.info(String.format(String.format("断言code成功：%s",ActualValue)));
        } else {
            Log.info(String.format(String.format("断言code失败：%s",ActualValue)));
            //Assert.assertEquals(0,false);
            //throw new RuntimeException(String.format("断言失败：%s",ActualValue));
            Assert.assertTrue(false);
        }
    }

    /**
     * 断言code
     */
    public static void assertNull(boolean isNull,String ActualValue) {
        if (isNull) {
            Log.info(String.format(String.format("断言null成功：%s",ActualValue)));
        } else {
            Log.info(String.format(String.format("断言null失败：%s",ActualValue)));
            //Assert.assertEquals(0,false);
            //throw new RuntimeException(String.format("断言失败：%s",ActualValue));
            Assert.assertTrue(false);
        }
    }
}
