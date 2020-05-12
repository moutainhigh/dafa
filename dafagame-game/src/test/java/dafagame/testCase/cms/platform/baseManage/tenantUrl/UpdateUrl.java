package dafagame.testCase.cms.platform.baseManage.tenantUrl;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class UpdateUrl {

    private static String updateUrl = "/v1/management/tenant/updateUrl";

    @Test(description = "修改厅主域名")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("id", "32")
                .addBuilder("urlId", "2")//1	WEB	前台,2	CMS	后台,3	MAINWEB	官网主域
                .addBuilder("tenantCode.txt", "duke")
                .addBuilder("url", "duke.dafagame-testCookie.com")
                .addBuilder("remark", "66")
                .fullBody();
        String result = DafaRequest.post(1, updateUrl, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
