package dafagame.testCase.front.userCenter;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;
import dafagame.common.SendPhoneCode;
import dafagame.common.VerifyPhoneCode;
import dafagame.common.VerifySafetyPassword;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.propertiesUtils.PropertiesUtil;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 安全密码
 */
public class SetOrResetSafetyPassword {
    private static String setOrResetSafetyPassword = "/v1/users/setOrResetSafetyPassword";

    @Test(description = "设置安全密码")//设置安全密码不需要验证手机号
    public static void test01() {
        //不需要验证安全密码
        String body = UrlBuilder.custom()
                .addBuilder("safetyPassword", DigestUtils.md5Hex("168169"))
                .fullBody();
        String result = DafaRequest.post(0,setOrResetSafetyPassword, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
    }

    @Test(description = "修改安全密码")
    public static void test02() {
        String phone = PropertiesUtil.getProperty("phone");//获取手机号
        String code = SendPhoneCode.getPhoneCode(phone,"3"); //获取验证码，1修改密码 2注册 3设置修改安全密码 4升级账号
        VerifyPhoneCode.verify(phone,code);//验证手机号

        VerifySafetyPassword.verify("safetyPassword");//验证安全密码（修改或者设置安全密码）
        String body = UrlBuilder.custom()
                .addBuilder("safetyPassword", DigestUtils.md5Hex("168169"))
                .fullBody();
        String result = DafaRequest.post(0,setOrResetSafetyPassword, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,phone+","+result);
    }

}
