package pers.testProductBug;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 新疆时时彩投注到未来期号20190721001
 */
public class testXJSSC {

    private static String cookie = "9AAD438CB27B0C19E2F8183992B3BDC3";
    private  static String url = "http://caishen02.com/v1/betting/addBetting";//
    private  static String addChaseBetting = "http://caishen02.com/v1/betting/addChaseBetting";


    @Test(description = "当天第一期")
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
        String bettingData = UrlBuilder.custom().addBuilder("bettingData", orders.toString()).fullBody();
        System.out.println(bettingData);
        System.out.println(DafaRequest.post(url, bettingData, cookie));
    }

    @Test(description = "当天第二期")
    public static void test0101() {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1001")
                .put("playDetailCode", "1001K11")
                .put("bettingNumber", "和大,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 1)
                .put("bettingPoint", "8")
                .put("bettingUnit", 1)
                .put("bettingIssue", "20190724002")
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

    @Test(description = "明天第一期")
    public static void test02() {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1001")
                .put("playDetailCode", "1001K11")
                .put("bettingNumber", "和大,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 1)
                .put("bettingPoint", "8")
                .put("bettingUnit", 1)
                .put("bettingIssue", "20190725001")
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

    @Test(description = "昨天第一期")
    public static void test03() {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1001")
                .put("playDetailCode", "1001K11")
                .put("bettingNumber", "和大,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 1)
                .put("bettingPoint", "8")
                .put("bettingUnit", 1)
                .put("bettingIssue", "20190723001")
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

    @Test(description = "昨天第二期")
    public static void test04() {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1001")
                .put("playDetailCode", "1001K11")
                .put("bettingNumber", "和大,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 1)
                .put("bettingPoint", "8")
                .put("bettingUnit", 1)
                .put("bettingIssue", "20190723002")
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

    @Test(description = "后天天第一期")
    public static void test05() {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1001")
                .put("playDetailCode", "1001K11")
                .put("bettingNumber", "和大,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 1)
                .put("bettingPoint", "8")
                .put("bettingUnit", 1)
                .put("bettingIssue", "20190726001")
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

    @Test(description = "下个月第一期")
    public static void test06() {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1001")
                .put("playDetailCode", "1001K11")
                .put("bettingNumber", "和大,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 1)
                .put("bettingPoint", "8")
                .put("bettingUnit", 1)
                .put("bettingIssue", "20190801001")
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

    @Test(description = "去年当月当日第一期")
    public static void test07() {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1001")
                .put("playDetailCode", "1001K11")
                .put("bettingNumber", "和大,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 1)
                .put("bettingPoint", "8")
                .put("bettingUnit", 1)
                .put("bettingIssue", "20180724001")
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
    @Test(description = "下个当日第一期")
    public static void test08() {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1001")
                .put("playDetailCode", "1001K11")
                .put("bettingNumber", "和大,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 1)
                .put("bettingPoint", "8")
                .put("bettingUnit", 1)
                .put("bettingIssue", "20190824001")
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


    @Test(description = "追号")
    public static void test09() {

        //追号的投注内容
        JSONObject eachInfo1 = JsonObjectBuilder.custom()
                .put("bettingNumber","大,大")
                .put("bettingCount", 1) //注数
                .bulid();

        JSONArray eachInfo = JsonArrayBuilder
                .custom()
                .addObject(eachInfo1)
                .bulid();

        //追号期数和追号的金额,倍数
        JSONObject eachOrder1 = JsonObjectBuilder.custom()
                .put("bettingIssue","20190724006")
                .put("graduationCount", 10) //倍数
                .put("bettingAmount",2)
                .bulid();

//        JSONObject eachOrder2 = JsonObjectBuilder.custom()
//                .put("bettingIssue","20190724006")
//                .put("graduationCount", 1) //倍数
//                .put("bettingAmount",1)
//                .bulid();

        JSONArray eachOrder = JsonArrayBuilder
                .custom()
                .addObject(eachOrder1)
                //.addObject(eachOrder2)
                .bulid();

        JSONObject chaseBettingData = JsonObjectBuilder.custom()
                .put("isStopAfterWinning",0)
                .put("startIssue","20190724006")
                .put("lotteryCode", "1001")
                .put("bettingPoint", "8")  //返点
                .put("playDetailCode", "1001I91")
                .put("bettingUnit", 0.1) //单位
                .put("bettingAllAmount", 1)
                .put("chaseCount", 1)  //追号期数
                .put("eachInfo",eachInfo)
                .put("eachOrder",eachOrder)
                .bulid();

        String bettingData = UrlBuilder.custom().addBuilder("chaseBettingData", chaseBettingData.toString()).fullBody();
        System.out.println(bettingData);
        System.out.println(DafaRequest.post(addChaseBetting, bettingData, cookie));
    }

    //1.追号的期数和 chaseCount不一致
    //2.



}
