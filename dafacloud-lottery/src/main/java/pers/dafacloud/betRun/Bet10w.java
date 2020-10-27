package pers.dafacloud.betRun;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.randomNameAddrIP.RandomIP;
import pers.utils.urlUtils.UrlBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bet10w {

    private static ExecutorService executors = Executors.newFixedThreadPool(100);
    private static String host = "http://52.77.207.64";
    private static String addBettingUrl = host + ":8020/v1/betting/addBetting";
    private static String addChaseBetting = host + ":8020/v1/betting/addChaseBetting";
    //private static String body = getBettingData();

    public static void main(String[] args) {
        run();
    }

    /*
     SELECT * FROM users where user_name LIKE 'yuebao%' limit 100;

     SELECT CONCAT(a.user_id,',',a.user_name,',',b.rebate_point)
     FROM users a INNER JOIN users_point b ON a.user_id = b.user_id and b.lottery_type = 0
     where a.user_name LIKE 'yuebao%'
     ORDER BY a.user_name limit 100;



     SELECT CONCAT(user_id,',',user_name)
     FROM balance_dafa
     where user_name LIKE 'yuebao%'
     and yuebao_balance = 100000
     ORDER BY user_name limit 100;

     update balance_dafa set balance = 100 where user_name LIKE 'yuebao%' and yuebao_balance = 100000;
     * */

    private static void run() {
        //List<String> users = FileUtil.readFile("/Users/duke/Documents/dafaUsers/dev2DafaYuebaoRebate.txt").subList(0, 20000);
        List<String> users = FileUtil.readFile("/Users/duke/Documents/dafaUsers/dev2DafaYuebaoRebateSSC.txt").subList(0, 20000);
        //List<String> users = FileUtil.readFile("/Users/hongzhong/Downloads/dev2DafaYuebaoRebateSSC.txt");
        for (int i = 0; i < users.size(); i++) {
            int finalI = i;
            executors.execute(() -> bettingExecu(users.get(finalI).split(",")));
            try {
                Thread.sleep(2);//每隔n秒启动一个线程
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //@Test(description = "测试")
    //public static void test01() {
    //    System.out.println("202010061223".substring(8));
    //}

    private static void bettingExecu(String[] userArray) {
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-user-id", userArray[0])
                .other("x-tenant-code", "dafa")
                .other("x-user-name", userArray[1])
                .other("x-source-Id", "1")
                .other("x-client-ip", RandomIP.getRandomIp())
                .build();
        HttpConfig httpConfig = HttpConfig.custom()
                //.url(addBettingUrl)
                .url(addChaseBetting)
                //.body(getBettingData(userArray[2]))
                .body(getChaseBetting(userArray[2]))
                .headers(headers);

        String result = DafaRequest.post(httpConfig);
        System.out.println(userArray[1] + " - " + result);
        if (!result.contains("投注成功")) {
            try {
                Thread.sleep(2700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //String result0 = DafaRequest.post(httpConfig.body(getBettingData(userArray[2])));
            String result0 = DafaRequest.post(httpConfig.body(getChaseBetting(userArray[2])));
            System.out.println(userArray[1] + " 重试- " + result0);
        }

    }


    private static String getChaseBetting(String rebate) {
        //追号的投注内容---------------------------------------------------------------------------
        JSONObject eachInfo1 = JsonObjectBuilder.custom()
                .put("bettingNumber", "9,-,-,-,-")
                .put("bettingCount", 1) //注数
                .bulid();

        JSONArray eachInfo = JsonArrayBuilder
                .custom()
                .addObject(eachInfo1)
                .bulid();

        //追号期数和追号的金额,倍数-----------------------------------------------------------------
        JsonArrayBuilder jsonArrayBuilder = JsonArrayBuilder
                .custom();
        int issue = Integer.parseInt(LotteryIssuePrivate.getCurrentIssue(1).substring(8))+1;//开始期数
        int tempIssue = issue;
        int count = 6;//追号期数，最高50期
        int tomorrow = 1440;//隔天
        int m = 20201007;
        int graduationCount = 100;
        int tempM = m;
        boolean falg = true;
        for (int i = 0; i < count; i++) {
            if (issue == 1 && falg) {
                m++;
                falg = false;
            }
            JSONObject eachOrder1 = JsonObjectBuilder.custom()
                    .put("bettingIssue", String.format("%s%03d", m, issue))
                    .put("graduationCount", graduationCount) //倍数
                    .put("bettingAmount", 2 * graduationCount)
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
                .put("lotteryCode", "1008")
                .put("bettingPoint", rebate)  //返点
                .put("playDetailCode", "1008A11")
                .put("bettingUnit", 1) //单位
                .put("bettingAllAmount", count * 2 * graduationCount)
                .put("chaseCount", count)  //追号期数
                .put("eachInfo", eachInfo)
                .put("eachOrder", eachOrder)
                .bulid();
        String bettingData = UrlBuilder.custom()
                .addBuilder("chaseBettingData", chaseBettingData.toString())
                //.addBuilder("useYeb", "1")
                .fullBody();
        //System.out.println(bettingData);
        return bettingData;
    }

    private static String getBettingData(String rebate) {
        JsonArrayBuilder jsonArrayBuilder = JsonArrayBuilder
                .custom();
        String[] betContentArray0 = "A10`双`1000.0000`1`1`1.00".split("@");
        for (int i = 0; i < betContentArray0.length; i++) {
            String[] betContentArray = betContentArray0[i].split("`");
            JSONObject order1 = JsonObjectBuilder.custom()
                    .put("lotteryCode", "1407")
                    .put("playDetailCode", "1407" + betContentArray[0])
                    .put("bettingNumber", betContentArray[1])
                    .put("bettingAmount", betContentArray[2])
                    .put("bettingCount", betContentArray[3])
                    //.put("bettingPoint", rebate.get(getLotteryType(betContentArray[0])))
                    .put("bettingPoint", rebate)
                    .put("bettingIssue", LotteryIssuePrivate.getCurrentIssue(1))
                    .put("graduationCount", betContentArray[4])
                    .put("bettingUnit", betContentArray[5])
                    .bulid();
            jsonArrayBuilder
                    .addObject(order1);
        }
        return UrlBuilder.custom()
                .addBuilder("bettingData", jsonArrayBuilder.bulid().toString())
                //.addBuilder("useYeb", "1")
                .fullBody();
    }
}
