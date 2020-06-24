package pers.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.Md5HA1.AESUtils;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.listUtils.ListSplit;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 充值压测
 */
public class RechargeCms {
    private static String host = "http://pt02.dafacloud-test.com";
    private static String updateSummaryPaymentRecordState = host + "/v1/transaction/updateSummaryPaymentRecordState";
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    /*
    SELECT *
            -- count(1)
    FROM summary_payment_record
    where created_date = '2020-05-29'
    and state = 0
    ORDER BY gmt_created
    desc limit 100;
    */
    public static void main(String[] args) {
        List<String> recordList = FileUtil.readFile(RechargeCms.class.getResourceAsStream("/test/a.txt"));

        System.out.println(recordList.size());
        String[] managments = {"duke", "alysia", "duck", "jessie", "ading","duke1","duke2","duke3","duke4","duke5","duke6"};
        List<List<String>> l = ListSplit.split(recordList, 5000);

        String loginBody;
        for (int i = 0; i < managments.length; i++) {
            Header[] headers = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .build();
            HttpConfig httpConfig = HttpConfig.custom()
                    //.url(updateSummaryPaymentRecordState)
                    .context(HttpCookies.custom()
                            //.setBasicClientCookie(host, "JSESSIONID", "BFED5C40499F5D7C048851FE8E8B11BE")
                            .getContext())
                    .headers(headers);
            String password = DigestUtils.md5Hex(managments[i] + DigestUtils.md5Hex("123456"));
            loginBody = String.format("managerName=%s&password=%s", managments[i], password);
            System.out.println(DafaRequest.post(httpConfig.url(host + "/v1/management/manager/login").body(loginBody)));
            System.out.println(DafaRequest.post(httpConfig.url(host + "/v1/management/manager/switchTenant").body("tenantCode=dafa")));
            int finalI = i;
            System.out.println(i);
            excutors.execute(() -> RechargeCms.task(httpConfig, l.get(finalI)));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void task(HttpConfig httpConfig, List<String> recordListSub) {
        String result;
        String body;
        System.out.println("recordListSub：" + recordListSub.size());
        for (int i = 0; i < recordListSub.size(); i++) {
            body = UrlBuilder.custom()
                    .addBuilder("id", recordListSub.get(i))
                    .addBuilder("operateType", "1")
                    .addBuilder("operatorRemark", "")
                    .addBuilder("flag", "1")
                    .fullBody();
            result = DafaRequest.post(httpConfig.body(body).url(updateSummaryPaymentRecordState));
            System.out.println(result);


        }

    }
}
