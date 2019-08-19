package dafagame.testCase.cms.platform.baseManage.tenantManage;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class UpdateTenantToken {

    private static String updateTenantToken = "/v1/management/tenant/updateTenantToken";

    @Test(description = "修改令牌")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("isToken", "1")//1开启 0关闭
               .addBuilder("tenantCode", "duke")
                .fullBody();
        String result = DafaRequest.post(1, updateTenantToken, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
