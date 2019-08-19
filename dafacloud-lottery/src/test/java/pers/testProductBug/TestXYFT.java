package pers.testProductBug;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

public class TestXYFT {


    private static String cookie = "FD6F3F2C0EA3494DDD75D85C54AAA34C";
    private  static String url = "http://caishen01.com/v1/betting/addBetting";//
    //private  static String addChaseBetting = "http://caishen02.com/v1/betting/addChaseBetting"; //追号


    @Test(description = "当天第一期")
    public static void test01() {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1309")
                .put("playDetailCode", "1309F11")
                .put("bettingNumber", "09,-,-,-,-,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 2)
                .put("bettingPoint", "6.7")
                .put("bettingUnit", 1)
                .put("bettingIssue", "20190807001")
                .put("graduationCount", 1)
                .bulid();
        JSONArray orders = JsonArrayBuilder
                .custom()
                .addObject(order1)
                .bulid();
        String bettingData = UrlBuilder.custom().addBuilder("bettingData", orders.toString()).fullBody();
        System.out.println(bettingData);
        System.out.println(DafaRequest.post(url, bettingData, cookie));
    }
}
