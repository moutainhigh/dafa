package pers.dafacloud.utils.persAnnontation;

@MyTestClass
public class TestCase {

    @MyTestCase(skip = true,name = "测试1")
    public void case1() {
        System.out.println("测试");
    }

}
