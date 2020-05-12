package dafagame.testCase.cms.platform.baseManage.tenantManage;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class UpdateStopTenantMaintenanceList {

    private static String updateStopTenantMaintenanceList = "/v1/management/tenant/updateStopTenantMaintenanceList";

    @Test(description = "停止维护")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("tenantCode.txt", "duke")
                .addBuilder("type", "2") //1 前台维护,2后台维护，3官网维护
                .fullBody();
        String result = DafaRequest.post(1, updateStopTenantMaintenanceList, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
