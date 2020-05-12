package dafagame.testCase.cms.platform.baseManage.tenantUrl;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class AddUrl {

    private static String addUrl = "/v1/management/tenant/addUrl";

    @Test(description = "添加厅主域名")
    public static void test01() {
        String body  = UrlBuilder.custom()
                .addBuilder("urlId","1")//1	WEB	前台,2	CMS	后台,3	MAINWEB	官网主域
                .addBuilder("tenantCode.txt","duke")
                .addBuilder("url","baidub.com") //后台网址必须使用二级域名
                .addBuilder("remark","测试")
                .fullBody();
        String result = DafaRequest.post(1,addUrl,body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
    }

}
