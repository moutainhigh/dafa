package dafagame.common;

import net.sf.json.JSONObject;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class SendPhoneCode {

    /**
     * 获取验证码
     * phoneCodeType 1修改密码 2注册 3修改安全密码 4升级账号
     */
    public static String getPhoneCode(String phone, String phoneCodeType) {
        String url = "/v1/users/sendPhoneCode";
        String body = UrlBuilder.custom()
                .addBuilder("phone", phone)
                .addBuilder("phoneCodeType", phoneCodeType)
                .fullBody();
        String result = DafaRequest.post(0, url, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, phone + "," + result);
        return jsonResult.getString("data");//验证码
    }

    public static void main(String[] args) {
        System.out.println(getPhoneCode("13012345673", "3"));
    }
}
