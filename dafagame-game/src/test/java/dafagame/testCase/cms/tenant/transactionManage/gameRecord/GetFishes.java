package dafagame.testCase.cms.tenant.transactionManage.gameRecord;


import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;

/**
 * 交易管理 >游戏记录查询 > 捕鱼列表
 */
public class GetFishes {
    private static String path = "/v1/game/getFishes";

    @Test(description = "测试")
    public static void test01() {
        String result = DafaRequest.get(1,path);
        System.out.println(result);
    }
}
