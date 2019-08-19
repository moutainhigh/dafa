package dafagame.testCase.front.userCenter;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class Nickname {
    private static String getRandomNickname = "/v1/users/getRandomNickname";

    private static String updateNickname = "/v1/users/updateNickname";


    @Test(description = "获取随机昵称")
    public static void test01() {
        String result = DafaRequest.get(0, getRandomNickname);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
    }

    @Test(description = "设置昵称")
    public static void test02() {
        String body  = UrlBuilder.custom()
                .addBuilder("nickName","沁源的进第")
                .fullBody();
        String result = DafaRequest.post(0,updateNickname,body);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code")==1,result);
    }
}
