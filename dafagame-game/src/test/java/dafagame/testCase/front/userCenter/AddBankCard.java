package dafagame.testCase.front.userCenter;


import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import dafagame.common.VerifySafetyPassword;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 添加银行卡
 */
public class AddBankCard {

    private static String path = "/v1/users/addBankCard";

    @Test(priority = 1, description = "添加银行卡")
    public void test001() {
        VerifySafetyPassword.verify("bankcard");//验证安全密码 "safetyPassword","bankcard",","withdraw"
        String body = UrlBuilder.custom()
                .addBuilder("bankName", "招商银行")
                .addBuilder("province", "北京")
                .addBuilder("city", "北京")
                .addBuilder("bankCardNumber", "622212345678")
                .addBuilder("accountName","杜克")
                .fullBody();
        String result = DafaRequest.post(0, path, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
