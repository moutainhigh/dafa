package pers.dafacloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


public class TestLog {

      Logger logger = LoggerFactory.getLogger(getClass());


    @Test(description = "测试")
    public  void test01() {

        logger.info("zxczx");
    }
}
