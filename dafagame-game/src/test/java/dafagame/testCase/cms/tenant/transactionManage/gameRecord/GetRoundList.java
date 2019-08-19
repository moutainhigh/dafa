package dafagame.testCase.cms.tenant.transactionManage.gameRecord;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 共用，游戏场次列表，场次下拉框来源
 */
public class GetRoundList {

    private static String path = "/v1/game/getRoundList";

    @Test(description = "测试")
    public static void test01() {
        String result = DafaRequest.get(path);

        AssertUtil.assertContains(result,"成功");

        JSONArray data = JSONObject.fromObject(result).getJSONArray("data");
        if(data.size()!=0){
            for (Object o : data) {
                System.out.println(o);
            }

        }

    }

}
