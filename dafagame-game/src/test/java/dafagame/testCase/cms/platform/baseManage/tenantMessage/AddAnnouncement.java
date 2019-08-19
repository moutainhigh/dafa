package dafagame.testCase.cms.platform.baseManage.tenantMessage;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class AddAnnouncement {
    private static String addAnnouncement = "/v1/management/manager/addAnnouncement";

    @Test(description = "新增平台消息")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("receiverList","") //
                .addBuilder("title", "标题1")
                .addBuilder("isTop", "1") //1置顶
                .addBuilder("content", "<p>内容</p>")
                .addBuilder("announcementId", "1") //消息类型 1 服务消息,2 产品消息,3 故障消息 ,4 支付消息
                .addBuilder("type", "1") //0 is_prox渠道, 1 is_driect直营
                .fullBody();
        String result = DafaRequest.post(1, addAnnouncement, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);

    }
}
