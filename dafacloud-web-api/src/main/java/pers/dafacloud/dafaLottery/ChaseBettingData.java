package pers.dafacloud.dafaLottery;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;

import java.util.List;

public class ChaseBettingData {

    public static String getChaseBettingData(String rebate) {
        //追号的投注内容
        JSONObject eachInfoObj = JsonObjectBuilder.custom()
                .put("bettingNumber", "5 6 7 8 9,-,-,-,-")
                .put("bettingCount", 5) //注数
                .bulid();

        JSONArray eachInfoList = JsonArrayBuilder
                .custom()
                .addObject(eachInfoObj)
                .bulid();

        List<String> issueList = LotteryIssuePrivate.getChaseIssueList(1);
        JsonArrayBuilder eachOrderList = JsonArrayBuilder
                .custom();
        for (String issue : issueList) {
            //追号期数和追号的金额,倍数
            JSONObject eachOrder = JsonObjectBuilder.custom()
                    .put("bettingIssue", issue)
                    .put("graduationCount", 1) //倍数
                    .put("bettingAmount", 10)
                    .bulid();
            eachOrderList.addObject(eachOrder);
        }
        JSONObject chaseBettingData = JsonObjectBuilder.custom()
                .put("isStopAfterWinning", 0)
                .put("startIssue", issueList.get(0))
                .put("lotteryCode", "1008")
                .put("bettingPoint", rebate)  //返点
                .put("playDetailCode", "1008A11")
                .put("bettingUnit", 1) //单位
                .put("bettingAllAmount", 100)
                .put("chaseCount", 10)  //追号期数
                .put("eachInfo", eachInfoList)
                .put("eachOrder", eachOrderList.bulid())
                .bulid();
        return chaseBettingData.toString();
    }
}
