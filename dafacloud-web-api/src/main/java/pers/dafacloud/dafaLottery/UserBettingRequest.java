package pers.dafacloud.dafaLottery;

import net.sf.json.JSONObject;
import pers.dafacloud.enums.LotteryConfig;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;
import pers.utils.jsonUtils.JsonUtils;
import pers.utils.randomNameAddrIP.RandomIP;

import java.util.List;

public class UserBettingRequest {
    ///v1/lottery/6hc/openTime
    ///v1/management/content/getAllLotteryDataFront

    private String host;
    private String rebate;

    public static void main(String[] args) {
        jsonFile();
    }

    //开奖计划json文件
    public static void jsonFile() {
        List<String> codes = LotteryConfig.getPublicLotteryCode();
        JSONObject jsonObject = new JSONObject();
        for (String lotteryCode : codes) {
            if ("1301".equals(lotteryCode) || "1201".equals(lotteryCode) || "1202".equals(lotteryCode)) {
                continue;
            }
            String result = DafaRequest.get(HttpConfig.custom()
                    .url("http://dafacloud-master.com/v1/lottery/openTime?lotteryCode=" + lotteryCode)
                    .headers(HttpHeader.custom()
                            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                            .other("X-Token", "QT7K5zmmzYDEtRLwSXQGA8UWry0Fd0Vu3gBlnGReIw+D5q4OTHfF5Wva1ehJEOX5")
                            .build()));
            jsonObject.put(lotteryCode, JSONObject.fromObject(result).getJSONArray("data"));
            //System.out.println(lotteryCode + ":" + result);
        }
        FileUtil.writeFile("/Users/duke/Documents/github/dafa/dafacloud-web-api/src/main/resources/openTime/all.json", JsonFormat.formatPrint(jsonObject.toString()), false);
    }


}
