package pers.test.yueBao;

import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.dafacloud.constant.LotteryConstant;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;

public class YueBaoFront {
    private static String host = LotteryConstant.host;
    //private static String host = "http://caishen03.com";

    private static Header[] headers = HttpHeader.custom()
            .contentType("application/x-www-form-urlencoded;charset=UTF-8")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
            .build();

    private static HttpConfig httpConfig = HttpConfig.custom()
            .headers(headers)
            .context(HttpCookies
                    .custom()
                    .setBasicClientCookie(host, "JSESSIONID", "428F2C3D896CF79C6D081733079C8829")
                    .getContext());

    private static String rechargeFrontPaymentRecord = host + "/v1/transaction/rechargeFrontPaymentRecord";

    public static void verifySafetyPassword() {
        String verifySafetyPassword = host + "/v1/users/verifySafetyPassword";
        String result = DafaRequest.post(httpConfig.url(verifySafetyPassword)
                .body("verifyType=yueBaoSafetyPassword&safetyPassword=9e888b495b2e23c27d165ac09f79d601"));
        System.out.println(result);

    }

    @Test(description = "测试")
    public static void test01() {
        String findYuebaoStatusFront = host + "/v1/balance/findYuebaoStatusFront?"; //余额宝状态
        System.out.println(DafaRequest.get(httpConfig.url(findYuebaoStatusFront)));
    }

    @Test(description = "测试")
    public static void test02() {
        String findYuebaoStatusFront = host + "/v1/balance/findUserYuebaoInfo"; //余额宝状态
        String result = DafaRequest.get(httpConfig.url(findYuebaoStatusFront));
        System.out.println(JsonFormat.formatPrint(result));
    }

    @Test(description = "测试")
    public static void test03() {
        String getYuebaoOperationInfo = host + "/v1/balance/getYuebaoOperationInfo"; //
        String result = DafaRequest.get(httpConfig.url(getYuebaoOperationInfo));
        System.out.println(JsonFormat.formatPrint(result));
    }

    @Test(description = "转入")
    public static void test04() {
        String transferMoney = host + "/v1/balance/transferMoney"; //
        String body = "money=100&direction=BR";
        verifySafetyPassword();
        String result = DafaRequest.post(httpConfig.url(transferMoney).body(body));
        System.out.println(JsonFormat.formatPrint(result));
    }

    @Test(description = "转出")
    public static void test05() {
        String transferMoney = host + "/v1/balance/transferMoney"; //
        String body = "money=100&direction=BC";
        verifySafetyPassword();
        String result = DafaRequest.post(httpConfig.url(transferMoney).body(body));
        System.out.println(JsonFormat.formatPrint(result));
    }


}
