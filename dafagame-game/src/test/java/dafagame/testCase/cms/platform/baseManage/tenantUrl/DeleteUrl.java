package dafagame.testCase.cms.platform.baseManage.tenantUrl;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class DeleteUrl {

    private static String deleteUrl = "/v1/management/tenant/deleteUrl";

    @Test(description = "厅主域名批量删除")
    public static void test01() {
        String body  = UrlBuilder.custom()
                .addBuilder("tenantCode","")
                .addBuilder("ids","")
                .fullBody();
        String result = DafaRequest.post(1,deleteUrl,body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
    }
}
