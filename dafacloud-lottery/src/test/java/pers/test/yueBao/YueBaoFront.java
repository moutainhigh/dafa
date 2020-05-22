package pers.test.yueBao;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.dafacloud.constant.LotteryConstant;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonFormat;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                    .setBasicClientCookie(host, "JSESSIONID", "0E84109AF2707A63CF35CB18A7F5DE4F")
                    .getContext());

    private static String rechargeFrontPaymentRecord = host + "/v1/transaction/rechargeFrontPaymentRecord";

    public static void verifySafetyPassword() {
        String verifySafetyPassword = host + "/v1/users/verifySafetyPassword";
        String result = DafaRequest.post(httpConfig.url(verifySafetyPassword)
                .body("verifyType=yueBaoSafetyPassword&safetyPassword=9e888b495b2e23c27d165ac09f79d601"));
        System.out.println(result);

    }

    @Test(description = "余额批量人工存入")
    public static void test01a() {
        String saveBatchManualRecord = host + "/v1/transaction/saveBatchManualRecord";
        List<String> list = FileUtil.readFile(YueBaoFront.class.getResourceAsStream("/users/dev2DafaIP2.txt"));
        int size = list.size();
        List<String> list0 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //System.out.println(list.get(i));
            list0.add(list.remove(0).split(",")[0]);
            if (list0.size() > 0 && list.size() <= 0) {
                String s0 = StringUtils.join(list0.toArray(), ",");
                System.out.println(s0);
                list0.clear();
                //String[] s = list.get(i).split(",");
                String body = UrlBuilder.custom()
                        .addBuilder("userName", s0)
                        .addBuilder("amount", "100000")
                        .addBuilder("remark", "1")
                        .addBuilder("dictionId", "402")
                        .fullBody();
                System.out.println(DafaRequest.post(httpConfig.url(saveBatchManualRecord).body(body)));
            }

            if (list0.size() > 99) {
                String s0 = StringUtils.join(list0.toArray(), ",");
                System.out.println(s0);
                list0.clear();
                String body = UrlBuilder.custom()
                        .addBuilder("userName", s0)
                        .addBuilder("amount", "100000")
                        .addBuilder("remark", "1")
                        .addBuilder("dictionId", "402")
                        .fullBody();
                System.out.println(DafaRequest.post(httpConfig.url(saveBatchManualRecord).body(body)));
            }
        }


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
        String transferMoney = host + "/v1/balance/transferMoney";
        String body = "money=100&direction=BR";
        verifySafetyPassword();
        String result = DafaRequest.post(httpConfig.url(transferMoney).body(body));
        System.out.println(JsonFormat.formatPrint(result));
    }

    @Test(description = "转出")
    public static void test05() {
        String transferMoney = host + "/v1/balance/transferMoney"; //
        String body = "money=10&direction=BC";
        verifySafetyPassword();
        String result = DafaRequest.post(httpConfig.url(transferMoney).body(body));
        System.out.println(JsonFormat.formatPrint(result));
    }


    @Test(description = "余额宝批量存入")
    public static void test06() {
        String host = "http://52.77.207.64:8090";
        String transferMoney = host + "/v1/balance/transferMoney";
        String verifySafetyPassword = host + "/v1/users/verifySafetyPassword";

        List<String> list = FileUtil.readFile(YueBaoFront.class.getResourceAsStream("/users/dev2DafaIP2.txt"));
        HttpConfig httpConfig;
        for (int i = 3229; i < list.size(); i++) {
            String[] userArray = list.get(i).split(",");
            Header[] headers = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .other("x-user-id", userArray[1])
                    .other("x-tenant-code", "dafa")
                    .other("x-user-name", userArray[0])
                    .other("x-token", "111")
                    .other("x-source-Id", "1")
                    .build();

            httpConfig = HttpConfig.custom()
                    .headers(headers)
                    .context(HttpCookies
                            .custom()
                            //.setBasicClientCookie(host, "JSESSIONID", "428F2C3D896CF79C6D081733079C8829")
                            .getContext());

            //String resultV = DafaRequest.post(httpConfig.url(verifySafetyPassword)
            //        .body("verifyType=yueBaoSafetyPassword&safetyPassword=9e888b495b2e23c27d165ac09f79d601"));
            //System.out.println(resultV);
            int amount = RandomUtils.nextInt(2, 10) * 100;
            String body = "money=" + amount + "&direction=BR";
            //verifySafetyPassword();
            String result = DafaRequest.post(httpConfig.url(transferMoney).body(body));
            System.out.println(i + " - " + userArray[0] + " - " + amount + " - " + result);
            //System.out.println(JsonFormat.formatPrint(result));
        }


    }


}
