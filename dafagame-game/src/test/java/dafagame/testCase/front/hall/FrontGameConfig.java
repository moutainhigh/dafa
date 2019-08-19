package dafagame.testCase.front.hall;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.logUtils.Log;

/**
 * 游戏列表
 *
 *  类型 以及 排序 1.hot热门，2.multi多人 ，3.battle 对战， 4.fishing捕鱼， 5.arcade街机
 *
 *  各个类型下的游戏 以及 排序
 *
 * */
public class FrontGameConfig {

    private static String frontGameConfig = "/v1/game/frontGameConfig";



    @Test(description = "前端,获取游戏配置信息")
    public static void test01() {
        String result = DafaRequest.get(0, frontGameConfig);
        JSONObject jsonResult = JSONObject.fromObject(result);
        AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
        JSONArray datas = jsonResult.getJSONObject("data").getJSONArray("frontGameSortList");
        if (datas.size() == 0) {
            AssertUtil.assertNull(false, "没有数据");
        }
        for (int i = 0; i < datas.size(); i++) {
            Log.info(datas.getJSONObject(i).toString());
        }
    }
}
