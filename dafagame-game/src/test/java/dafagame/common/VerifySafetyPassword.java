package dafagame.common;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;


public class VerifySafetyPassword {


    public static void main(String[] args) {
        verify("bankcard");
    }

    /**
     * 验证安全密码
     * "safetyPassword","bankcard",","withdraw"
     */
    public static void verify(String verifyType) {
        String url = "/v1/users/verifySafetyPassword?";
        String body = UrlBuilder.custom()
                .addBuilder("safetyPassword", DigestUtils.md5Hex("168169"))
                .addBuilder("verifyType", verifyType)
                .fullBody();
        String result = DafaRequest.post(0, url, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
