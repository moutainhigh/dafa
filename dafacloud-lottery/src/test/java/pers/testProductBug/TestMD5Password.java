package pers.testProductBug;

import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;


//后台 》平台 》 系统运维》会员重置 》重置登陆密码 =  重置登陆密码是010101 ，安全密码是020202
//会员管理修改密码是：是可以修改成任意
//安全密码申述：
//前台 》安全中心》找回安全密码》通过身份识别找回》
// 1。登陆密码，2。最近提现（不是首张）已经锁定银行卡（卡号和用户名）》如果近期有修改安全密码，就会进入人工审核，上传身份证和银行卡号
//
//验证银行卡 ： safetyCheckBankCard

public class TestMD5Password {

    @Test(description = "简单安全密码加密")
    public static void test001() {
        String[] passwords = {"111111", "222222", "333333", "444444", "555555", "666666", "777777",
                "888888", "999999", "000000", "123456", "654321", "123123",
                "020202","010101","112233","223344","334455","445566","556677","667788","778899","987654","876543","765432","010101","121212","232323","343434","454545","565656","676767","787878","898989","111222","222333","333444","444555","555666","666777","777888","888999"};
        for (String s : passwords) {
            String password = DigestUtils.md5Hex("caike22" + DigestUtils.md5Hex(s));
            if ("44fb7eaf0342315c39db70cc4d8ae789".equals(password)) {
                System.out.println(s + "简单安全密码：" + password);
            } else {
                System.out.println(password);
            }

        }
    }

    @Test(description = "简单登陆密码")
    public static void test01() {
        String[] passwords = {
                "112233",
                "223344",
                "334455",
                "445566",
                "556677",
                "667788",
                "778899",
                "987654",
                "876543",
                "765432",
                "010101",
                "121212",
                "232323",
                "343434",
                "454545",
                "565656",
                "676767",
                "787878",
                "898989",
                "111222",
                "222333",
                "333444",
                "444555",
                "555666",
                "666777",
                "777888",
                "888999"};
        for (String s : passwords) {
            String password = DigestUtils.md5Hex("ch070402" + DigestUtils.md5Hex(s));
            System.out.println(password);
            if (s.equals("")) {
                System.out.println(password + "," + s);
            }
        }


    }
}
