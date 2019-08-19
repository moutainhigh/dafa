package dafagame.testCase.cms.platform.baseManage.tenantManage;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class AddIp {

    private static String addIp = "/v1/management/tenant/addIp";

    @Test(description = "测试")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("tenantCode", "duke")
                .addBuilder("ip", "192.168.2.1")
                .addBuilder("remark", "平台专员duke添加")
                .fullBody();
        String result = DafaRequest.post(1, addIp, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }

}
