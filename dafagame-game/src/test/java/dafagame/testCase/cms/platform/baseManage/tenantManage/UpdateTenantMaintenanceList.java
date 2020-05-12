package dafagame.testCase.cms.platform.baseManage.tenantManage;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class UpdateTenantMaintenanceList {
    private static String updateTenantMaintenanceList = "/v1/management/tenant/updateTenantMaintenanceList";

    @Test(description = "修改维护时间")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("tenantCode.txt", "duke")
                .addBuilder("type", "2") //1 前台维护,2后台维护，3官网维护
                .addBuilder("endDate", "2019-08-19 17:33:20")
                .fullBody();
        String result = DafaRequest.post(1, updateTenantMaintenanceList, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
