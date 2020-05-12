package dafagame.testCase.cms.platform.baseManage.tenantManage;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class DeleteIp {
    private static String deleteIp = "/v1/management/tenant/deleteIp";

    @Test(description = "批量删除cms绑定的ip")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("tenantCode.txt", "duke")
                .addBuilder("ids", "31,29")
                .fullBody();
        String result = DafaRequest.post(1, deleteIp, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
