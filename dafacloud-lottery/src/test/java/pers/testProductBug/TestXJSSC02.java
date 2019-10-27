package pers.testProductBug;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 新疆时时彩 第043跨天撤单问题
 */
public class TestXJSSC02 {
    private static String host = "http://52.76.195.164:8020";
    private static String addBettingUrl = host + "/v1/betting/addBetting";
    private static String sysCancelBetting = host + "/v1/betting/sysCancelBetting";
    private static String addChaseBetting = host +  "/v1/betting/addChaseBetting";
    private static String cancelBetting = host + "/v1/betting/cancelBetting";
    private static String[] userArray = {"dafa", "50000511", "dafai0001"};
    private static HttpHeader httpHeader = HttpHeader
            .custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .other("x-tenant-code", userArray[0])
            .other("x-user-id", userArray[1])
            .other("x-user-name", userArray[2])
            .other("x-source-Id", "1");

    //平常期号 43 - 48 期
    //平常撤单，43 - 48 期撤单
    //平常追号撤单，43 - 48 期追号撤单
    //过了封盘期撤单撤单
    @Test(description = "20191010042")
    public static void test01a() {
        Header[] headers = httpHeader.other("test-now", "2019-10-10 23:39:59").build();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(getBetContent("20191010042")).headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "," + userArray[2] + "," + result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "20191010043")
    public static void test01() {
        Header[] headers = httpHeader.other("test-now", "2019-10-10 23:59:59").build();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(getBetContent("20191010043")).headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "," + userArray[2] + "," + result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "20191010043")
    public static void test01b() {
        Header[] headers = httpHeader.other("test-now", "2019-10-11 00:00:01").build();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(getBetContent("20191010043")).headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "," + userArray[2] + "," + result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "20191010044")
    public static void test02() {
        Header[] headers = httpHeader.other("test-now", "2019-10-11 00:19:59").build();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(getBetContent("20191010044")).headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "," + userArray[2] + "," + result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "20191010045")
    public static void test03() {
        Header[] headers = httpHeader.other("test-now", "2019-10-11 00:39:59").build();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(getBetContent("20191010045")).headers(headers);
        String result;
        result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "," + userArray[2] + "," + result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "20191010046")
    public static void test04() {
        Header[] headers = httpHeader.other("test-now", "2019-10-11 00:59:59").build();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(getBetContent("20191010046")).headers(headers);
        String result;
        result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "," + userArray[2] + "," + result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "20191010047")
    public static void test05() {
        Header[] headers = httpHeader.other("test-now", "2019-10-11 01:19:59").build();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(getBetContent("20191010047")).headers(headers);
        String result;
        result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "," + userArray[2] + "," + result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "20191010048")
    public static void test06() {
        Header[] headers = httpHeader.other("test-now", "2019-10-11 01:39:59").build();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(getBetContent("20191010048")).headers(headers);
        String result;
        result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "," + userArray[2] + "," + result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void test07() {
        Header[] headers = httpHeader.other("test-now", "2019-10-11 01:59:59").build();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(getBetContent("20191011001")).headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "," + userArray[2] + "," + result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void test08() {
        Header[] headers = httpHeader.other("test-now", "2019-10-10 23:39:59").build();
        HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(getBetContent("20191010042")).headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + "," + userArray[2] + "," + result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test //系统撤单
    public static void test09() {
        String[] manager = {"system", "100035", "duke"};
        HttpHeader httpHeader = HttpHeader
                .custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-tenant-code", manager[0])
                .other("x-manager-id", manager[1])
                .other("x-manager-name", manager[2]);
        Header[] headers = httpHeader.other("test-now", "2019-10-11 01:39:59").build();
        String body = "lotteryCode=1001&issue=20191010044&remark=123";
        HttpConfig httpConfig =
                HttpConfig.custom()
                        .url(sysCancelBetting)
                        .body(body)
                        .headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test //自行撤单
    public static void test10() {
        Header[] headers = httpHeader.other("test-now", "2019-10-11 01:17:59").build();
        String body = "orderId=64000041162486";
        HttpConfig httpConfig =
                HttpConfig.custom()
                        .url(cancelBetting)
                        .body(body)
                        .headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test //追号
    public static void test10a() {
        //2019-10-10 23:59:59 2019-10-11 00:01:59
        Header[] headers = httpHeader.other("test-now", "2019-10-11 00:01:59").build();
        String body = "chaseBettingData={\"isStopAfterWinning\":1,\"startIssue\":\"20191010043\",\"lotteryCode\":\"1001\",\"bettingPoint\":\"8\"" +
                ",\"playDetailCode\":\"1001A11\",\"bettingUnit\":1,\"bettingAllAmount\":14,\"chaseCount\":7,\"eachInfo\":" +
                "[{\"bettingNumber\":\"-,-,-,-,9\",\"bettingCount\":1}],\"eachOrder\":" +
                "[{\"bettingIssue\":\"20191010043\",\"graduationCount\":1,\"bettingAmount\":2}" +
                ",{\"bettingIssue\":\"20191010044\",\"graduationCount\":1,\"bettingAmount\":2}" +
                ",{\"bettingIssue\":\"20191010045\",\"graduationCount\":1,\"bettingAmount\":2}," +
                "{\"bettingIssue\":\"20191010046\",\"graduationCount\":1,\"bettingAmount\":2}," +
                "{\"bettingIssue\":\"20191010047\",\"graduationCount\":1,\"bettingAmount\":2}," +
                "{\"bettingIssue\":\"20191010048\",\"graduationCount\":1,\"bettingAmount\":2}," +
                "{\"bettingIssue\":\"20191011001\",\"graduationCount\":1,\"bettingAmount\":2}]}";
        HttpConfig httpConfig =
                HttpConfig.custom()
                        .url(addChaseBetting)
                        .body(body)
                        .headers(headers);
        String result = DafaRequest.post(httpConfig);
        System.out.println(result);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getBetContent(String bettingIssue) {
        JSONObject order1 = JsonObjectBuilder.custom()
                .put("lotteryCode", "1001")
                .put("playDetailCode", "1001K11")
                .put("bettingNumber", "和大,-,-,-,-,-")
                .put("bettingCount", 1)
                .put("bettingAmount", 1)
                .put("bettingPoint", "8")
                .put("bettingUnit", 1)
                .put("bettingIssue", bettingIssue)
                .put("graduationCount", 1)
                .bulid();
        JSONArray orders = JsonArrayBuilder
                .custom()
                .addObject(order1)
                .bulid();
        String bettingData = UrlBuilder.custom().addBuilder("bettingData", orders.toString()).fullBody();
        return bettingData;
    }

}
