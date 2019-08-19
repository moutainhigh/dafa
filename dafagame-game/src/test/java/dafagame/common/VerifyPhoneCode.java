package dafagame.common;

import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.jsonUtils.JsonOfObject;
import pers.utils.jsonUtils.Response;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 手机验证
 */
public class VerifyPhoneCode {

    private static String verifyPhoneCode = "/v1/users/verifyPhoneCode";

    public static void verify(String phone, String code) {
        String body = UrlBuilder.custom()
                .addBuilder("phone", phone)
                .addBuilder("code", code)
                .fullBody();
        String result = DafaRequest.post(0, verifyPhoneCode, body);
        Response response = JsonOfObject.jsonToObj(Response.class, result);
        AssertUtil.assertCode(response.isSuccess(), result);
    }

}
