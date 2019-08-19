package dafagame.testCase.front.agentCenter.subGameRecord;

import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

/**
 *代理 > 下级游戏记录 > 下级上庄记录
 * */
public class FrontAgentBankerGameRecord {

    private static String path = "/v1/game/frontAgentBankerGameRecord";

    @Test(description = "下级上庄记录")
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
