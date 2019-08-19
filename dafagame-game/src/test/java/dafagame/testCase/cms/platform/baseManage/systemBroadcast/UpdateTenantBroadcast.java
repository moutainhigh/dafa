package dafagame.testCase.cms.platform.baseManage.systemBroadcast;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class UpdateTenantBroadcast {
    private static String updateTenantBroadcast = "/v1/management/tenant/updateTenantBroadcast";

    //-- broadcast_type 1游戏大厅 2游戏房间 3后台
    //-- receiver_type ：
    //-- 1游戏大厅：1所有厅主 2直营厅主 3渠道厅主 4指定厅主
    //-- 2游戏房间：1所有游戏 2指定游戏
    //-- 3后台：1所有厅主 2直营厅主 3渠道厅主 4指定厅主
    @Test(description = "修改系统广播")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("broadcastType", "1")//1游戏大厅 2游戏房间 3后台
                .addBuilder("receiverName", "")
                .addBuilder("receiverType", "1")
                .addBuilder("receiverList", "") //指定接收厅主
                .addBuilder("content", "内容")
                .addBuilder("startTime", "2019-08-01 00:00:00")
                .addBuilder("endTime", "2019-08-20 00:00:00")
                .addBuilder("id", "1")
                .fullBody();
        String result = DafaRequest.post(1, updateTenantBroadcast, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);

    }
}
