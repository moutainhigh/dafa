package pers.testProductBug;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

public class testBettingByIP {


    @Test(description = "测试")
    public static void test01() {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1001")
                .put("playDetailCode", "1001K11")
                .put("bettingNumber", "和大,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 1)
                .put("bettingPoint", "8")
                .put("bettingUnit", 1)
                .put("bettingIssue", "20190724001")
                .put("graduationCount", 1)
                .bulid();
        JSONArray orders = JsonArrayBuilder
                .custom()
                .addObject(order1)
                .bulid();
        Header[] headers = HttpHeader
                .custom()
                .contentType("")
                .build();
        String bettingData = UrlBuilder.custom().addBuilder("bettingData", orders.toString()).fullBody();
        System.out.println(bettingData);
        //System.out.println(DafaRequest.post(url, bettingData, cookie));
        String.format("");
    }


}
