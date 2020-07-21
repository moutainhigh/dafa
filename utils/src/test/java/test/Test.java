package test;

import cn.binarywang.tools.generator.BankCardNumberGenerator;
import cn.binarywang.tools.generator.ChineseAddressGenerator;
import cn.binarywang.tools.generator.ChineseIDCardNumberGenerator;

public class Test {

    @org.testng.annotations.Test(description = "测试")
    public  void test01() {
        System.out.println( ChineseAddressGenerator.getInstance()
                .generate());
        System.out.println(ChineseIDCardNumberGenerator.getInstance().generate());
        //System.out.println(BankCardNumberGenerator.generate(BankNameEnum.ICBC, BankCardTypeEnum.CREDIT));
        String bankCardNo = BankCardNumberGenerator.getInstance().generate();
        System.out.println(bankCardNo);
    }
}
