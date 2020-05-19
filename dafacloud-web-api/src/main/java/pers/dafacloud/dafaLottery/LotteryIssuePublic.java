package pers.dafacloud.dafaLottery;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import pers.dafacloud.constant.LotteryConstant;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.timeUtils.TimeUtil;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URL;
import java.time.LocalDateTime;

public class LotteryIssuePublic {

    private static String host = LotteryConstant.host;
    private static String openTime = "/v1/lottery/openTime";

    /**
     * 获取官方彩开奖计划
     */
    private static JSONArray getLotteryOpenPlan(int lotteryCode, HttpConfig httpConfig) {
        if (httpConfig == null) {
            return null;
        }
        String url = UrlBuilder.custom()
                .url(openTime)
                .addBuilder("lotteryCode", lotteryCode)
                .fullUrl();
        httpConfig.url(url);
        String result0 = DafaRequest.get(httpConfig);
        return JSONObject.fromObject(result0).getJSONArray("data");
    }


    /**
     * 获取官方彩期号
     */
    public static String getPublicCurrentIssue(int lotteryCode, HttpConfig httpConfig) throws Exception {
        JSONArray ja = getLotteryOpenPlan(lotteryCode, httpConfig);

        LocalDateTime localDateTime = LocalDateTime.now();
        int hourNow = localDateTime.getHour();
        int minuteNow = localDateTime.getMinute();

        String currentIssue = "";
        for (int i = 0; i < ja.size(); i++) {
            JSONObject lotteryOpen = ja.getJSONObject(i);
            String startTime = lotteryOpen.getString("startTime").split(" ")[1];
            String endTime = lotteryOpen.getString("endTime").split(" ")[1];
            if (TimeUtil.isEffectiveDate(String.format("%s:%s:00", hourNow, minuteNow), startTime, endTime)) {
                currentIssue = lotteryOpen.getString("issue");
            }
        }
        return currentIssue;
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        int lotteryCode = 1309;
        String ip = RandomIP.getRandomIp();
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-forwarded-for", ip)
                .other("x-remote-IP", ip)
                .other("X-Real-IP", ip)
                //.other("x-tenant-code", "dafa")
                //.other("x-source-Id", "3")
                //.other("x-user-id", "50000511")
                //.other("x-user-name", "dafai0001 ")
                //.other("x-client-ip", "118.143.214.129")
                //.other("x-url", "dafacloud.com")
                .build();

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie basicClientCookie = new BasicClientCookie("JSESSIONID", "A06D618351A0114C1EAEBCA9BB4D7137");
        basicClientCookie.setDomain(new URL(host).getHost());
        basicClientCookie.setPath("/");
        cookieStore.addCookie(basicClientCookie);
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);

        HttpConfig httpConfig = HttpConfig.custom().headers(headers).context(context);

        String issue = LotteryIssuePublic.getPublicCurrentIssue(lotteryCode, httpConfig);
        System.out.println(issue);

    }
}
