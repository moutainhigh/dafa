package dafagame.testCase.cms.platform.gameManagement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URLEncoder;

/**
 * 平台管理 > 游戏管理 >多人游戏
 * dafagame_game.round_config 游戏配置表，默认配置和配置
 */
public class MultiGameList {

    private static String multiGameList = "/v1/game/multiGameList";
    private static String setMultiGameRoom = "/v1/game/setMultiGameRoom";//房间设置
    private static String setMultiGameRobot = "/v1/game/setMultiGameRobot";//机器人设置
    private static String setMultiGameBanker = "/v1/game/setMultiGameBanker";//多人游戏上庄配置
    private static String setMultiGameChip = "/v1/game/setMultiGameChip";//筹码配置


    @Test(description = "获取多人游戏配置列表")
    public static void test01() {
        String result = DafaRequest.get(1,
                UrlBuilder.custom()
                        .url(multiGameList)
                        .addBuilder("gameName", "")
                        .addBuilder("gameCode", "")
                        .fullUrl());
        System.out.println("默认值");
        JSONArray jaDefault = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("default");//默认值
        for (Object o : jaDefault) {
            System.out.println(o);
        }
        System.out.println("实际值");
        JSONArray jaList = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("list");//真实值
        for (Object o : jaList) {
            System.out.println(o);
        }
    }

    @Test(description = "房间设置")
    public static void test02() throws Exception{
        String body = UrlBuilder.custom()
                .addBuilder("gameCode", "104")
                .addBuilder("room",
                        URLEncoder.encode("[{\"id\":5,\"entryLimit\":\"16\",\"playerLimit\":200,\"bettingLimit\":30}]"
                        ,"UTF-8"))
                .fullBody();
        String result = DafaRequest.post(1,setMultiGameRoom, body);
        System.out.println(result);
    }

    @Test(description = "机器人设置")
    public static void test03() throws Exception{
        //banker 上庄机器人，player陪玩机器人
        //min max count（最低机器人数量）
        String body = UrlBuilder.custom()
                .addBuilder("gameCode", "104")
                .addBuilder("id","5")
                .addBuilder("robot",
                        URLEncoder.encode(
                                "{\"banker\":{\"min\":5,\"max\":10,\"count\":\"6\"},\"player\":{\"min\":20,\"max\":30,\"count\":6}}"
                                ,"UTF-8"))
                .fullBody();
        String result = DafaRequest.post(1,setMultiGameRobot, body);
        System.out.println(result);
    }

    @Test(description = "上庄配置，修改金额和次数")
    public static void test04() throws Exception{
        String body = UrlBuilder.custom()
                .addBuilder("gameCode", "104")
                .addBuilder("id","5")
                .addBuilder("banker",
                        URLEncoder.encode(
                                "{\"times\":3,\"amount\":9001,\"system\":1,\"player\":\"0\",\"robot\":\"0\"}"
                                ,"UTF-8"))
                .fullBody();
        String result = DafaRequest.post(1,setMultiGameBanker, body);
        AssertUtil.assertContains(result,"成功");

    }
    @Test(description = "上庄配置,关闭系统上庄,异常测试")
    public static void test0401() throws Exception{
        String body = UrlBuilder.custom()
                .addBuilder("gameCode", "104")
                .addBuilder("id","5")
                .addBuilder("banker",
                        URLEncoder.encode(
                                "{\"times\":2,\"amount\":9000,\"system\":0,\"player\":\"0\",\"robot\":\"0\"}"
                                ,"UTF-8"))
                .fullBody();
        String result = DafaRequest.post(1,setMultiGameBanker, body);
        System.out.println(result);
    }
    @Test(description = "上庄配置,玩家上庄0，机器人上庄1,异常测试")
    public static void test0402() throws Exception{
        String body = UrlBuilder.custom()
                .addBuilder("gameCode", "104")
                .addBuilder("id","5")
                .addBuilder("banker",
                        URLEncoder.encode(
                                "{\"times\":2,\"amount\":9000,\"system\":1,\"player\":\"0\",\"robot\":\"1\"}"
                                ,"UTF-8"))
                .fullBody();
        String result = DafaRequest.post(1,setMultiGameBanker, body);
        System.out.println(result);
    }
    @Test(description = "上庄配置,玩家上庄0,机器人上庄1,异常测试")
    public static void test0403() throws Exception{
        String body = UrlBuilder.custom()
                .addBuilder("gameCode", "104")
                .addBuilder("id","5")
                .addBuilder("banker",
                        URLEncoder.encode(
                                "{\"times\":2,\"amount\":9000,\"system\":1,\"player\":\"0\",\"robot\":\"1\"}"
                                ,"UTF-8"))
                .fullBody();
        String result = DafaRequest.post(1,setMultiGameBanker, body);
        System.out.println(result);
        //{"msg":"机器人上庄需同步关闭","code":-1,"data":null} 测试ok
    }

    @Test(description = "筹码配置")
    public static void test05() throws Exception{
        String body = UrlBuilder.custom()
                .addBuilder("gameCode", "104")
                .addBuilder("id","5")
                .addBuilder("chip",
                        URLEncoder.encode(
                                "[5,10,100,500,5000,10000]"
                                ,"UTF-8"))
                .fullBody();
        String result = DafaRequest.post(1,setMultiGameChip, body);
        AssertUtil.assertContains(result,"成功");
    }

    @Test(description = "筹码配置,筹码包含小数")
    public static void test0501() throws Exception{
        String body = UrlBuilder.custom()
                .addBuilder("gameCode", "104")
                .addBuilder("id","5")
                .addBuilder("chip",
                        URLEncoder.encode(
                                "[5,10,100,500,5000.1,10000]"
                                ,"UTF-8"))
                .fullBody();
        String result = DafaRequest.post(1,setMultiGameChip, body);
        AssertUtil.assertContains(result,"错误");
    }

}
