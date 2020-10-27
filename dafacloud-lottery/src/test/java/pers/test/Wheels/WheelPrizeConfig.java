package pers.test.Wheels;

import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

public class WheelPrizeConfig {
    private static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .build();
    private static String updatePrizeWheelsConfig = "http://pt01.dafacloud-test.com/v1/activity/prizeWheels/updatePrizeWheelsConfig";

    private static HttpConfig httpConfig = HttpConfig.custom()
            .headers(headers)
            .url(updatePrizeWheelsConfig).context(HttpCookies.custom()
                    .setBasicClientCookie("http://pt01.dafacloud-test.com","JSESSIONID","A6B57011E95C10DB09852E57E40121EE")
                    .getContext());


    @Test(description = "测试")
    public static void test01() {
        String body = UrlBuilder.custom()
                .addBuilder("status", "1")
                .addBuilder("prizeWheelsActivityName", "测试账号")
                .addBuilder("activityTimeType", "2") //1只有开始时间，2有开始和结束时间
                .addBuilder("beginTime", "2020-10-04")//开始时间
                .addBuilder("endTime", "2020-10-28")//结束时间
                .addBuilder("wheelStyleType", "1")
                .addBuilder("prizeNumber", "2") //奖品数量
                .addBuilder("prizeContent", "{\"prizes\":[{\"type\":1,\"prize\":1.88},{\"type\":2,\"prize\":\"iphone\"}]}")//奖品
                .addBuilder("prizeProbabilityType", "3") //中奖概率：1按奖品，2按等级，3按分组
                .addBuilder("prizeProbability", "{\"probabilities\":[{\"level\":0,\"probability\":\"50,50\"},{\"level\":1,\"probability\":\"50,50\"},{\"level\":2,\"probability\":\"50,50\"},{\"level\":3,\"probability\":\"50,50\"},{\"level\":4,\"probability\":\"50,50\"},{\"level\":5,\"probability\":\"50,50\"},{\"level\":6,\"probability\":\"50,50\"},{\"level\":7,\"probability\":\"50,50\"}]}")
                .addBuilder("multiple", "1") //流水倍数
                .addBuilder("prizeConditionType", "2") //抽奖条件 1有抽奖条件 2 无抽奖条件 3按周期充值额 4按周期投注额
                .addBuilder("prizeCondition", "{\"rules\":[]}")//抽奖条件规则
                .addBuilder("prizeTimesType", "1") //1统一次数，2.按用户等级，3.按用户组别
                .addBuilder("prizeTimes", "{\"prizeTimesArr\":[{\"level\":1,\"prizeTimes\":1}]}")
                .addBuilder("prizePeriodType", "2") //1活动期间只参与一次 2当日 3每天 4每周 5每月
                .addBuilder("pcTitle", "pcxczxcxzcxz")
                .addBuilder("mbTitle", "mbxczxcxzcxz")
                .addBuilder("pcContent", Content.pcContent)
                .addBuilder("mbContent", Content.mbContent)
                .addBuilder("pcTitleImg", "/test-activity/dafa/1601369879871.png")
                .addBuilder("mbTitleImg", "/test-activity/dafa/1601369879995.png")
                .addBuilder("id", "102")
                .addBuilder("pcid", "222")
                .addBuilder("mbid", "221")
                .addBuilder("tabSequence", "2")
                .fullBody();
        httpConfig.body(body);
        String result= DafaRequest.post(httpConfig);
        System.out.println(result);

    }
}
