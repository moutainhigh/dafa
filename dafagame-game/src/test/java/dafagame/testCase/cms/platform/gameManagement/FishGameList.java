package dafagame.testCase.cms.platform.gameManagement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URLEncoder;

public class FishGameList {

    private static String fishGameList = "/v1/game/fishGameList";
    private static String setFishGameRoom = "/v1/game/setFishGameRoom";

    @Test(description = "查询")
    public static void test0101() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(fishGameList)
                        .addBuilder("gameName", "")
                        .addBuilder("gameCode", "")
                        .fullUrl());
        System.out.println(result);
        System.out.println("默认值");
        JSONArray jaDefault = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("default");//默认值
        for (Object o : jaDefault) {
            System.out.println(o);
        }
        System.out.println("真实值");
        JSONArray jaList = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("list");//真实值
        for (Object o : jaList) {
            System.out.println(o);
        }
    }

    @Test(description = "游戏设置")
    public static void test0201() throws Exception {
        String result = DafaRequest.post(1,setFishGameRoom,
                UrlBuilder.custom()
                        .addBuilder("gameCode", "301")
                        .addBuilder("room", URLEncoder.encode(
                                "[{\"id\":24,\"entryLimit\":\"20\",\"bulletMin\":\"0.01\",\"bulletMax\":\"10.0\"}," +
                                        "{\"id\":25,\"entryLimit\":100,\"bulletMin\":\"1\",\"bulletMax\":\"0.1\"}," +
                                        "{\"id\":26,\"entryLimit\":300,\"bulletMin\":\"0.1\",\"bulletMax\":\"1.0\"}," +
                                        "{\"id\":27,\"entryLimit\":500,\"bulletMin\":\"1.0\",\"bulletMax\":\"10.0\"}]", "utf-8"))
                        .fullBody());
        System.out.println(result);
    }

}
