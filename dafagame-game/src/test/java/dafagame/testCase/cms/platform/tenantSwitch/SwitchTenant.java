package dafagame.testCase.cms.platform.tenantSwitch;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class SwitchTenant {
    private static String switchTenant = "/v1/management/manager/switchTenant";

    @Test(description = "切换厅主")
    public static void test01() {
        String body  = UrlBuilder.custom()
                .addBuilder("tenantCode.txt","duke")
                .fullBody();
        String result = DafaRequest.post(1,switchTenant,body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);

    }

}
