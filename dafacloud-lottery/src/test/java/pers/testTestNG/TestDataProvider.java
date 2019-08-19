package pers.testTestNG;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pers.dafacloud.utils.report.ZTestReport;

import java.io.IOException;

@Listeners(ZTestReport.class)
public class TestDataProvider {

   final static String s = "1";

    @DataProvider(name = "dataInfo")
    protected Object[][] dataInfo1() throws IOException {
        //Object[][] myObj = new Object[][]{{"90"}, {"100"}};
        Object[][] myObj = new Object[][]{{"90", "c"}, {"100", "a"}};
        return myObj;
    }

    @Test(dataProvider = "dataInfo",description = s)
    public static void test01(String a, String b) {
        System.out.println("hello:" + a + "," + b);
    }

}
