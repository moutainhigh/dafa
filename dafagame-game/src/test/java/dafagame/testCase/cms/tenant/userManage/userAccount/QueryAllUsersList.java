package dafagame.testCase.cms.tenant.userManage.userAccount;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;
import pers.utils.timeUtils.TimeUtil;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 用户管理 》用户账号
 *
 *user和user_info
 *
 *
 */
public class QueryAllUsersList {

    private static String queryAllUsersList = "/v1/users/queryAllUsersList";

    @Test(description = "查询所有用户")
    public static void test01() {
        String url = UrlBuilder.custom()
                .url(queryAllUsersList)
                .addBuilder("agentGrade", "-1")
                .addBuilder("userType","0")
                .addBuilder("startTime",TimeUtil.getLCTime(-30))
                .addBuilder("endTime", TimeUtil.getLCTime(1))
                .addBuilder("pageNum","1")
                .addBuilder("pageSize","20")
                .fullUrl();
        String result = DafaRequest.get(1, url);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray datas = jsonResult.getJSONObject("data").getJSONArray("rows");
        if (datas.size() == 0) {
            AssertUtil.assertNull(false, "没有数据");
        }
        Log.info("当前页数据量："+datas.size());
        Log.info("总数据量：" + jsonResult.getJSONObject("data").getInt("total"));
        for (int i = 0; i < datas.size(); i++) {
            Log.info(datas.getJSONObject(i).toString());
        }
    }


}
