package dafagame.testCase.cms.platform.baseManage.tenantManage;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class UpdateIp {
    private static String updateIp = "/v1/management/tenant/updateIp";

    @Test(description = "修改cms绑定的ip")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("tenantCode.txt", "duke")
                .addBuilder("ip", "192.168.2.2")
                .addBuilder("remark", "平台专员duke添加")
                .addBuilder("id", "30")
                .fullBody();
        String result = DafaRequest.post(1, updateIp, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }

}

