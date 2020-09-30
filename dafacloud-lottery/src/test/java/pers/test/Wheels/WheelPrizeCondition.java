package pers.test.Wheels;

import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;
import pers.utils.urlUtils.UrlBuilder;

/**
 * 抽奖条件
 */
public class WheelPrizeCondition {
    //private static String[] user = {"test", "50000298", "duke011"};
    private static String[] user = {"dafa", "50000511", "dafai0001"};
    private static Header[] headers = HttpHeader.custom()
            //.contentType("application/x-www-form-urlencoded;charset=UTF-8")
            //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .other("x-tenant-code", user[0])
            //.other("x-source-Id", "3")
            .other("x-user-id", user[1])
            .other("x-user-name", user[2])
            //.other("x-client-ip", "118.143.214.129")
            .other("x-is-test", "0")
            //.other("x-url", "dafacloud.com")
            .build();
    private static String host = "http://52.76.195.164";

    private static HttpConfig httpConfig = HttpConfig.custom()
            .headers(headers);

    public static void main(String[] args) {

    }

    /**
     *
     */
    @Test(description = "累计充值，累计投注（彩票和棋牌）")
    public static void userProfit() {
        String url = host + ":8060/v1/report/userReport/turntableProfit?startTime=2020-09-23&endTime=2020-09-29";
        httpConfig.url(url);
        String result = DafaRequest.get(httpConfig);
        System.out.println(JsonFormat.formatPrint(result));
    }

    /**
     *
     */
    @Test(description = "单笔投注金额（彩票 和 游戏）")
    public static void getMaxBettingAmount() {
        String url0 = host + ":8020/v1/betting/getMaxBettingAmountByDate?userId=50000511&tenantCode=dafa&startDate=2020-08-26&endDate=2020-09-30";
        httpConfig.url(url0);
        String result0 = DafaRequest.get(httpConfig);
        System.out.println(JsonFormat.formatPrint(result0));

        //String url1 = "http://a4cdf4aef23dc11ea8f02061e82846b5-26ec5fa4ac0099ca.elb.ap-east-1.amazonaws.com/v1/game/getMaxBettingAmount?userId=50000510&tenantCode=dafa";
        //httpConfig.url(url1);
        //String result1 = DafaRequest.get(httpConfig);
        //System.out.println(JsonFormat.formatPrint(result1));
    }

    /**
     *
     */
    @Test(description = "单笔充值金额")
    public static void queryMaxRechargeAmount() {
        String url0 = host + ":8030/v1/transaction/queryMaxRechargeAmount?userName=dafai0001&tenantCode=dafa&startDate=2020-09-01";
        httpConfig.url(url0);
        String result0 = DafaRequest.get(httpConfig);
        System.out.println(JsonFormat.formatPrint(result0));

        //String url1 = "http://a4cdf4aef23dc11ea8f02061e82846b5-26ec5fa4ac0099ca.elb.ap-east-1.amazonaws.com/v1/game/getMaxBettingAmount?userId=50000510&tenantCode=dafa";
        //httpConfig.url(url1);
        //String result1 = DafaRequest.get(httpConfig);
        //System.out.println(JsonFormat.formatPrint(result1));
    }
}
