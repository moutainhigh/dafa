package dafagame.testCase.cms.platform.gameManagement;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URLEncoder;

/**
 *平台管理 > 游戏管理 > 对战游戏
 * */
public class BattleGameList {

    private static String path = "/v1/game/battleGameList";
    private static String setBattleGameRoom = "/v1/game/setBattleGameRoom";
    private static String setBattleGameRobot = "/v1/game/setBattleGameRobot";
    private static String setBattleGameBasis = "/v1/game/setBattleGameBasis";


    @Test(description = "查询所有列表")
    public static void test01() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(path)
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

    @Test(description = "按gameName查询")
    public static void test0101() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(path)
                        .addBuilder("gameName", "炸金花")
                        .addBuilder("gameCode", "")
                        .fullUrl());
        //System.out.println(result);
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
    @Test(description = "按gameCode查询")
    public static void test0102() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(path)
                        .addBuilder("gameName", "")
                        .addBuilder("gameCode", "201")
                        .fullUrl());
        //System.out.println(result);
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

    @Test(description = "房间设置")
    public static void test0201() throws Exception{
        String result = DafaRequest.post(1,setBattleGameRoom,
                UrlBuilder.custom()
                        .addBuilder("gameCode", "201")
                        .addBuilder("room",
                                URLEncoder.encode("[{\"id\":8,\"entryLimit\":10,\"baseLimit\":1},{\"id\":9,\"entryLimit\":50,\"baseLimit\":2},{\"id\":10,\"entryLimit\":300,\"baseLimit\":5},{\"id\":11,\"entryLimit\":600,\"baseLimit\":10}]","utf-8"))
                        .fullBody());
        System.out.println(result);
    }

    @Test(description = "机器人设置,优先机器人")
    public static void test0301() throws Exception{
        String result = DafaRequest.post(1,setBattleGameRobot,
                UrlBuilder.custom()
                        .addBuilder("gameCode", "201")
                        .addBuilder("id", "8")
                        .addBuilder("robot",
                                URLEncoder.encode("{\"default\":\"robot\",\"level1\":{\"weight\":20,\"match\":true},\"level2\":{\"weight\":40,\"match\":\"false\"},\"level3\":{\"weight\":20,\"match\":\"false\"},\"level4\":{\"weight\":30,\"match\":true}}","utf-8"))
                        .fullBody());
        System.out.println(result);
    }

    @Test(description = "机器人设置,优先玩家")
    public static void test0302() throws Exception{
        String result = DafaRequest.post(1,setBattleGameRobot,
                UrlBuilder.custom()
                        .addBuilder("gameCode", "201")
                        .addBuilder("id", "8")
                        .addBuilder("robot",
                                URLEncoder.encode("{\"default\":\"player\",\"level1\":{\"weight\":20,\"match\":true},\"level2\":{\"weight\":40,\"match\":\"false\"},\"level3\":{\"weight\":20,\"match\":\"false\"},\"level4\":{\"weight\":30,\"match\":true}}","utf-8"))
                        .fullBody());
        System.out.println(result);
    }

    @Test(description = "机器人设置,优先玩家")
    public static void test0401() throws Exception{
        String result = DafaRequest.post(1,setBattleGameRobot,
                UrlBuilder.custom()
                        .addBuilder("gameCode", "201")
                        .addBuilder("id", "8")
                        .addBuilder("robot",
                                URLEncoder.encode("{\"default\":\"player\",\"level1\":{\"weight\":20,\"match\":true},\"level2\":{\"weight\":40,\"match\":\"false\"},\"level3\":{\"weight\":20,\"match\":\"false\"},\"level4\":{\"weight\":30,\"match\":true}}","utf-8"))
                        .fullBody());
        System.out.println(result);
    }

    @Test(description = "基础设置,炸金花")
    public static void test0501() throws Exception{
        String result = DafaRequest.post(1,setBattleGameBasis,
                UrlBuilder.custom()
                        .addBuilder("gameCode", "201")
                        .addBuilder("id", "8")
                        .addBuilder("basis",
                                URLEncoder.encode("{\"bet\":[0.3,0.4,0.6,0.8,1],\"maxBet\":\"10\",\"maxRing\":\"10\"}",
                                        "utf-8"))
                        .fullBody());
        System.out.println(result);
    }

    @Test(description = "基础设置,抢庄牛牛")
    public static void test0502() throws Exception{
        String result = DafaRequest.post(1,setBattleGameBasis,
                UrlBuilder.custom()
                        .addBuilder("gameCode", "202")
                        .addBuilder("id", "13")
                        .addBuilder("basis",
                                URLEncoder.encode("{\"bankerMulti\":[2,2,3,4],\"betMulti\":[5,10,15,20]}",
                                        "utf-8"))
                        .fullBody());
        System.out.println(result);
    }
}
