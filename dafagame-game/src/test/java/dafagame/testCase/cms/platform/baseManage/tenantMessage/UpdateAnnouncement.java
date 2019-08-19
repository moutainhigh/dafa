package dafagame.testCase.cms.platform.baseManage.tenantMessage;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class UpdateAnnouncement {

    private static String updateAnnouncement = "/v1/management/manager/updateAnnouncement";

    //    -- 1	SERVICE	   服务消息
//-- 2	PRODUCT	产品消息
//-- 3	ERROR	故障消息
//-- 4	PAYMENT	支付消息
    @Test(description = "更新平台消息")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("receiverList", "duke")
                .addBuilder("title", "标题")
                .addBuilder("isTop", "0") //1置顶
                .addBuilder("content", "<p>内容1</p>")
                .addBuilder("announcementId", "1") //消息类型 1 服务消息,2 产品消息,3 故障消息 ,4 支付消息
                .addBuilder("type", "")
                .addBuilder("id", "33")
                .addBuilder("reads", "")
                .addBuilder("isRead", "")
                .addBuilder("isDirect", "")
                .addBuilder("isProxy", "")
                .addBuilder("gmtPublished", "")
                .addBuilder("addressee", "")
                .addBuilder("gmtCreated", "")
                .addBuilder("gmtModified", "")
                .fullBody();
        String result = DafaRequest.post(1, updateAnnouncement, body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }
}
