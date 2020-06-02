package pers.test;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.dafacloud.enums.LotteryAttribute;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

public class TestCmsManualOpenLottery {

    private static String host = "http://pt02.dafacloud-test.com";

    private static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .build();

    private static HttpConfig httpConfig = HttpConfig.custom()
            .headers(headers)
            .context(HttpCookies
                    .custom()
                    .setBasicClientCookie(host, "JSESSIONID", "1BEB4CC3B89B4EC2AE98FD114C228BA8")
                    .getContext());

    @Test(description = "手动开奖")
    public static void test01() {
        String manualOpen = host + "/v1/lottery/manualOpen";
        String lotteryName = "幸运飞艇";
        String body = UrlBuilder.custom()
                .addBuilder("lotteryCode", LotteryAttribute.getLotteryCodebyName(lotteryName))
                .addBuilder("lotteryName", lotteryName)
                .addBuilder("issue", "20200525030")
                .addBuilder("openNumber", "10,09,08,07,06,05,04,03,02,01")
                .addBuilder("remark", "测试环境测试")
                .fullBody();
        String result = DafaRequest.post(httpConfig.url(manualOpen).body(body));
        System.out.println(result);
    }


    /**
     * INSERT INTO `dafacloud_lottery`.`lottery_open_message`(`lottery_code`, `issue`, `open_number`, `open_time`, `is_open`, `is_manual`)
     * VALUES ('1309', '20200601037', '10,09,08,07,06,05,04,03,02,01', '2020-06-01 15:47:11.000', 0, 0);
     * */
    @Test(description = "开奖纠错")
    public static void test02() {
        String updateOpenNumber = host + "/v1/lottery/updateOpenNumber";
        String lotteryName = "幸运飞艇";
        String lotteryCode = LotteryAttribute.getLotteryCodebyName(lotteryName);
        if (StringUtils.isEmpty(lotteryCode)) {
            System.out.println(lotteryName + "的lotteryCode 获取 空");
            return;
        }
        String body = UrlBuilder.custom()
                .addBuilder("lotteryCode", lotteryCode)
                .addBuilder("lotteryName", lotteryName)
                .addBuilder("issue", "20200601037")
                .addBuilder("validateIssue", "20200601037")
                .addBuilder("openNumber", "01,02,03,04,05,06,07,08,09,10")
                .addBuilder("validateOpenNumber", "01,02,03,04,05,06,07,08,09,10")
                .addBuilder("reAward", "true")
                .addBuilder("reStat", "true")

                .addBuilder("remark", "测试环境测试")
                .fullBody();
        String result = DafaRequest.post(httpConfig.url(updateOpenNumber).body(body));
        System.out.println(result);

    }

}
