package dafagame.testCase.cms.tenant.tenantManage.baseManage.PictureManage;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;

public class GetAllLogo {
    private static String getAllLogo = "/v1/management/content/getAllLogo";

    @Test(description = "测试")
    public static void test01() {
        String result = DafaRequest.get(1, getAllLogo);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
        JSONObject data = jsonResult.getJSONObject("data");
        Log.info(data.getString("1"));// /testCookie-management/logo/MasterLogo.png //大厅Logo ， 登陆界面的log
        Log.info(data.getString("2"));// /testCookie-management/logo/WebLogo.png //官网Logo ，
        Log.info(data.getString("3"));// /testCookie-management/logo/CMSLogo.png //后台登录页Logo
        Log.info(data.getString("4"));// /testCookie-management/cindy/logo/gameicon.png //


        //src="http://18.162.159.75:7480/test-management/logo/MasterLogo.png?1565349335509"


    }
}
