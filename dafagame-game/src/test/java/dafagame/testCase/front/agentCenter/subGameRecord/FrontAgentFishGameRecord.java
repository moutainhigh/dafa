package dafagame.testCase.front.agentCenter.subGameRecord;

import org.testng.annotations.Test;
import pers.utils.urlUtils.UrlBuilder;
import pers.utils.dafaRequest.DafaRequest;


/**
 * 代理 > 下级游戏记录 > 下级捕鱼游戏记录
 */
public class FrontAgentFishGameRecord {

    private static String path = "/v1/game/frontAgentFishGameRecord";

    @Test(description = "测试")
    public static void test01() {
        String result = DafaRequest.get(0,
                UrlBuilder.custom()
                        .url(path)
                        .addBuilder("pageSize", "10")
                        .addBuilder("pageNum", "1")
                        .fullUrl());
        System.out.println(result);
    }

}
