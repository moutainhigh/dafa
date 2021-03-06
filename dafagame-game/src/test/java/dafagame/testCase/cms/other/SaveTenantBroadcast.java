package dafagame.testCase.cms.other;

import org.testng.annotations.Test;
import pers.dafagame.enums.Path;

import java.util.HashMap;
import java.util.Map;

/**
 * 新增广播（后台POST）
 */
public class SaveTenantBroadcast {

    static Path path = Path.saveTenantBroadcast;

    @Test(description = "新增广播")
    public void test001() {
        String body = "broadcastType=2&receiverType=1&content=后台-指定厅主222&startTime=2019-05-22 15:22:00&endTime=2019-05-23 16:00:09&receiverList=";
        Map<String, String> headers = new HashMap<>();
        headers.put("x-manager-name", "duke");//用户名
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    }
}
