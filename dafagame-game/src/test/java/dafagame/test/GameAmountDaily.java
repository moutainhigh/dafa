package dafagame.test;

import com.alibaba.fastjson.JSONObject;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;

import java.util.ArrayList;
import java.util.Arrays;

/***
 *报表给游戏服务每天盈利数据，内部接口
 * */
public class GameAmountDaily {
    private static final String HOST = "http://a4cdf4aef23dc11ea8f02061e82846b5-26ec5fa4ac0099ca.elb.ap-east-1.amazonaws.com";

    /**
     * 杀率控制，game_profit表
     */
    private static String gameAmountDaily = HOST + "/v1/report/platformReport/gameAmountDaily?date=2020-02-28";

    /**
     * 在线人数
     */
    private static String gameOnlineCount = HOST + "/v1/game/gameOnlineCount?gameCode=104";

    /**
     * 运营数据
     */
    private static final String gameProfit = HOST + "/v1/report/gameReport/gameProfit?gameCode=";

    private static final String[] GAME_CODES = {"101", "102", "103", "104", "105", "106"};
    private static final ArrayList<String> gameCodes = new ArrayList<>(Arrays.asList("101", "102", "103", "104", "105", "106"));


    public static void main(String[] args) {
        /**
         * 杀率控制，game_profit表
         */
        for (Object obj : JSONObject.parseObject(DafaRequest.get(HttpConfig.custom().url(gameAmountDaily))).getJSONArray("data")) {
            JSONObject jsonObject = (JSONObject) obj;
            if (gameCodes.contains(jsonObject.getString("gameCode"))) {
                System.out.println(jsonObject.getString("betAmount") + "," + jsonObject.getString("profitAmount"));
                //System.out.println(obj);
            }
        }

        /**
         * 运营数据
         */
        //for (String gameCode : GAME_CODES) {
        //    System.out.println(DafaRequest.get(HttpConfig.custom().url(gameProfit + gameCode)));
        //}
        //System.out.println(DafaRequest.get(HttpConfig.custom().url(gameOnlineCount)));
    }
}
