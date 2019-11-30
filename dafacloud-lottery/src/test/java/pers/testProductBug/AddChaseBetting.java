package pers.testProductBug;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

public class AddChaseBetting {

    private static String addChaseBetting = "http://caishen02.com/v1/betting/addChaseBetting";
    private static String addChaseBettingIP = "http://10.168.68.77:8020/v1/betting/addChaseBetting";//乘风本地ip

    /**
     * 幸运飞艇追号功能，跨天最后一期是180期，凌晨4点，跨天日期不变
     */
    @Test(description = "测试")
    public static void test01() {
        //追号的投注内容---------------------------------------------------------------------------
        JSONObject eachInfo1 = JsonObjectBuilder.custom()
                .put("bettingNumber", "-,-,-,-,-,-,-,-,-,10")
                .put("bettingCount", 1) //注数
                .bulid();

        JSONArray eachInfo = JsonArrayBuilder
                .custom()
                .addObject(eachInfo1)
                .bulid();

        //追号期数和追号的金额,倍数-----------------------------------------------------------------
        JsonArrayBuilder jsonArrayBuilder = JsonArrayBuilder
                .custom();
        int issue = 132;//开始期数
        int tempIssue = issue;
        int count = 50;//追号期数，最高50期
        int tomorrow = 180;//隔天
        int m = 20191104;
        int tempM = m;
        boolean falg = true;
        for (int i = 0; i < count; i++) {
            if (issue == 1 && falg) {
                m++;
                falg = false;
            }
            JSONObject eachOrder1 = JsonObjectBuilder.custom()
                    .put("bettingIssue", String.format("%s%03d", m, issue))
                    .put("graduationCount", 1) //倍数
                    .put("bettingAmount", 2)
                    .bulid();
            jsonArrayBuilder.addObject(eachOrder1);

            issue++;
            if (issue > tomorrow) {
                issue = 1;
            }
        }
        JSONArray eachOrder = jsonArrayBuilder.bulid();
        JSONObject chaseBettingData = JsonObjectBuilder.custom()
                .put("isStopAfterWinning", 0)
                .put("startIssue", String.format("%s%03d", tempM, tempIssue))
                .put("lotteryCode", "1309")
                .put("bettingPoint", "8")  //返点
                .put("playDetailCode", "1309F11")
                .put("bettingUnit", 1) //单位
                .put("bettingAllAmount", count * 2)
                .put("chaseCount", count)  //追号期数
                .put("eachInfo", eachInfo)
                .put("eachOrder", eachOrder)
                .bulid();
        String bettingData = UrlBuilder.custom().addBuilder("chaseBettingData", chaseBettingData.toString()).fullBody();
        System.out.println(bettingData);

        String ip = RandomIP.getRandomIp();
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-forwarded-for", ip)
                .other("x-remote-IP", ip)
                .other("X-Real-IP", ip)
                .other("x-tenant-code", "dafa")
                .other("x-source-Id", "3")
                .other("x-user-id", "50000511")
                .other("x-user-name", "dafai0001 ")
                .other("x-client-ip", "118.143.214.129")
                .other("x-url", "dafacloud.com")
                .build();
        HttpCookies httpCookies = HttpCookies.custom();
        HttpConfig httpConfig = HttpConfig.custom().url(addChaseBetting).body(bettingData).headers(headers).context(httpCookies.getContext());
        String result0 = DafaRequest.post(httpConfig);
        System.out.println("aaa:"+ result0);

        //String result = DafaRequest.post(addChaseBetting, bettingData, "3D898969F3806CDDF730C8DF918C7342");
        //System.out.println(result);
    }

}
