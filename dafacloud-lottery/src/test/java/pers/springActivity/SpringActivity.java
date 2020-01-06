package pers.springActivity;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SpringActivity {
    private static String host = "http://cms.caishen02.com";
    private static String hostIP = "http://52.76.195.164:8080";
    private static String lotteryDraw = hostIP + "/v1/activity/lotteryDraw";
    private static String getDrawLog = hostIP + "/v1/activity/getDrawLog";
    private static String getSpringFestivalTenant = hostIP + "/v1/activity/getSpringFestivalTenant";
    private static String getSpringFestivalTenantList = hostIP + "/v1/activity/getSpringFestivalTenantList";

    private static ExecutorService excutors = Executors.newFixedThreadPool(500);

    public static void main(String[] args) throws Exception {
        List<String> list = FileUtil.readFile(SpringActivity.class.getResourceAsStream("/springTenantCode.txt"));
        Collections.shuffle(list);
        CountDownLatch cdl = new CountDownLatch(list.size());
        for (int i = 0; i < list.size(); i++) {
            String[] strs = list.get(i).split(",");
            excutors.execute(() -> lotteryDrawTask(strs[0], Integer.parseInt(strs[1]), strs[2], cdl));
        }
        if (cdl.await(6000000, TimeUnit.SECONDS)) {
            excutors.shutdown();
        }
        //lotteryDrawTask("dafa",1,cdl);
    }

    /**
     * 通过ip 抽奖
     *
     * @param tenantCode 站长编码
     * @param num        抽奖次数
     */
    public static void lotteryDrawTask(String tenantCode, int num, String manageId, CountDownLatch cdl) {
        for (int i = 0; i < num; i++) {
            Header[] headers = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0")
                    .other("x-manager-name", tenantCode + "88")
                    .other("x-manager-id", manageId)
                    .other("x-tenant-code", tenantCode)
                    .build();
            HttpConfig httpConfig = HttpConfig.custom()
                    .url(lotteryDraw)
                    .headers(headers)
                    .body("");
            System.out.println(tenantCode + " - " + (i + 1) + "-" + DafaRequest.post(httpConfig));
            try {
                Thread.sleep(30 * 1000); //8秒抽一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cdl.countDown();
    }

    @Test(description = "登录-单线程春节抽奖接口")
    public static void test01() throws Exception {
        //System.out.println(Math.random());//Math.random()
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("x-manager-name", "caisw88")
                //.other("x-manager-id","23456")
                .other("x-tenant-code", "caisw")
                .build();
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie basicClientCookie = new BasicClientCookie("JSESSIONID", "4CF6291CA022BA938C629473136C2618");
        basicClientCookie.setDomain(new URL(host).getHost());
        basicClientCookie.setPath("/");
        cookieStore.addCookie(basicClientCookie);
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);

        HttpConfig httpConfig = HttpConfig.custom()
                .url(lotteryDraw)
                .headers(headers)
                //.context(context)
                .body("");
        //String body = UrlBuilder.custom().addBuilder("x-tenant-code","").fullBody();
        for (int i = 0; i < 1; i++) {
            System.out.println(i + "-" + DafaRequest.post(httpConfig));
        }
    }

    @Test(description = "登录-抽奖记录接口")
    public static void test02() throws Exception {
        System.out.println(DafaRequest.get(httpConfig().url(getDrawLog).body("")));
    }

    @Test(description = "登录-站点信息接口")
    public static void test03() throws Exception {
        System.out.println(DafaRequest.get(httpConfig().url(getSpringFestivalTenant).body("")));
    }

    @Test(description = "登录-春节站长活动记录")
    public static void test04() throws Exception {
        String url = UrlBuilder.custom().url(getSpringFestivalTenantList).addBuilder("pageSize", "10").addBuilder("pageNum", "1").fullUrl();
        System.out.println(DafaRequest.get(httpConfig().url(url)));
    }

    public static HttpConfig httpConfig() throws Exception {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("x-manager-name", "dafa")
                .other("x-tenant-code", "caisw")
                .build();
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie basicClientCookie = new BasicClientCookie("JSESSIONID", "C100727D63EFE0B398067400BC6509DD");
        basicClientCookie.setDomain(new URL(host).getHost());
        basicClientCookie.setPath("/");
        cookieStore.addCookie(basicClientCookie);
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);
        return HttpConfig.custom().headers(headers);
    }
}
