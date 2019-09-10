package pers.dafacloud;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import pers.dafacloud.page.beting.InitializaIssueEndtime;
import pers.utils.ThreadSleep.ThreadSleep;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 站长彩种压测
 */
public class ThreadTenantLottery1 {
    //52.76.195.164:8020 1套
    //52.77.207.64:8020
    private static String url = "http://52.76.195.164:8020/v1/betting/addBetting";

    private static int count = 0;

    private static int timeCount = 0;

    //private static String getServerTimeMillisecond = "http://52.76.195.164:8020/v1/betting/getServerTimeMillisecond";

    //static List<String> betContents1k = FileUtil.
    //        readFile(ThreadTenantLottery1.class.getResourceAsStream("/svBetContent/1418.txt"));
    //static List<String> betContents5k = FileUtil.
    //        readFile(ThreadTenantLottery1.class.getResourceAsStream("/svBetContent/1419.txt"));
    //static List<String> betContents1s = FileUtil.
    //        readFile(ThreadTenantLottery1.class.getResourceAsStream("/svBetContent/1018.txt"));
    //static List<String> betContents5s = FileUtil.
    //        readFile(ThreadTenantLottery1.class.getResourceAsStream("/svBetContent/1019.txt"));

    /**
     * 1418 站長快3
     * 1419 站長5分快3
     * <p>
     * 1018 站長时时彩
     * 1019 站長5分时时彩
     * <p>
     * 1个站彩种5000，1个站1000
     * 系统异常，该站的彩种未创建
     */
    public static void main(String[] args) throws Exception {
        Timer timerOne = new Timer();
        timerOne.schedule(new TimerTask() {
            @Override
            public void run() {
                timeCount++;
                Calendar calendar = Calendar.getInstance();
                //System.out.println(calendar.get(calendar.SECOND));
                if (calendar.get(calendar.SECOND) == 59) {
                    System.out.println("成功数据条数：" + (count));
                    count=0;
                }
            }
        }, 0, 1000);

        List<String> betContents1k = FileUtil.
                readFile(ThreadTenantLottery1.class.getResourceAsStream("/svBetContent/1418.txt"));
        //List<String> betContents5k = FileUtil.
        //        readFile(ThreadTenantLottery1.class.getResourceAsStream("/svBetContent/1419.txt"));
        List<String> betContents1s = FileUtil.
                readFile(ThreadTenantLottery1.class.getResourceAsStream("/svBetContent/1018.txt"));
        //List<String> betContents5s = FileUtil.
        //        readFile(ThreadTenantLottery1.class.getResourceAsStream("/svBetContent/1019.txt"));

        List<String> users = FileUtil.
                readFile(ThreadTenantLottery1.class.getResourceAsStream("/svBetContent/ca.txt"));
        System.out.println(users.size());
        List<List<String>> list = new ArrayList<>();
        list.add(betContents1k);
        //list.add(betContents5k);
        list.add(betContents1s);
        //list.add(betContents5s);
       // betting(users, list, 1);


    }

    public static void betting(List<String> users1, List<List<String>> betContents1, int sleepSecond1) {
        for (int i = 0; i < users1.size(); i++) { //用户
            List<List<String>> betContents = betContents1;
            List<String> users = users1;
            int index = i;
            int sleepSecond = sleepSecond1;
            new Thread(() -> {
                String[] userArray = users.get(index).split(",");
                //System.out.println(users.get(0));
                Header[] headers = HttpHeader.custom()
                        .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                        .other("x-user-id", userArray[0])
                        .other("x-tenant-code", userArray[1])
                        .other("x-user-name", userArray[2])
                        .other("x-source-Id", "1")
                        .other("Origin", "http://52.76.195.164")
                        .build();
                //投注
                for (int j = 0; j < 1000000000; j++) {
                    int betContentsIndex = (int) (Math.random() * betContents.size());
                    int betContentIndex = (int) (Math.random() * (betContents.get(betContentsIndex).size()));
                    String betContent = betContents.get(betContentsIndex).get(betContentIndex);
                    String[] betContentArray = betContent.split("`");
                    long now = System.currentTimeMillis();
                    long lcMillTime = 0;
                    String currentDate = "";
                    try {
                        SimpleDateFormat sdfOne = new SimpleDateFormat("yyyyMMdd");
                        lcMillTime = sdfOne.parse(sdfOne.format(now)).getTime();
                        Date date = new Date();
                        currentDate = sdfOne.format(date);
                        //System.out.println(currentDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int second = (int) (now - lcMillTime) / 1000;//距离今日凌晨秒数
                    int issueOneNum = second / 60 + 1;//期数

                    int issueFiveNum = second / (60 * 5) + 1;//期数

                    JsonObjectBuilder order1 = JsonObjectBuilder.custom()
                            .put("lotteryCode", betContentArray[0])
                            .put("playDetailCode", betContentArray[1])
                            .put("bettingNumber", betContentArray[2])
                            .put("bettingAmount", betContentArray[3])
                            .put("bettingCount", betContentArray[4])
                            .put("bettingPoint", "7")
                            //.put("bettingIssue", String.format("%s%04d", currentDate, issueOneNum))
                            .put("graduationCount", betContentArray[5])
                            .put("bettingUnit", betContentArray[6]);

                    if (betContentArray[0].equals("1418") || betContentArray[0].equals("1018")) {
                        order1.put("bettingIssue", String.format("%s%04d", currentDate, issueOneNum));
                    } else {
                        order1.put("bettingIssue", String.format("%s%03d", currentDate, issueFiveNum));
                    }
                    JSONArray orders = JsonArrayBuilder
                            .custom()
                            .addObject(order1.bulid())
                            .bulid();
                    String bettingData = UrlBuilder.custom().addBuilder("bettingData", orders.toString()).fullBody();
                    String result = "";
                    HttpConfig httpConfig = HttpConfig.custom().url(url).body(bettingData).headers(headers);
                    try {
                        //result = DafaRequest.post(url, bettingData, headers);
                        result = DafaRequest.post(httpConfig);
                    } catch (Exception e) {
                        System.out.println("======" + e);
                    }

                    //System.out.println(betContentIndex + "," + bettingData);
                    System.out.println(userArray[1] + "," + userArray[2] + "," + result);
                    //System.out.println("----------------------------");
                    ThreadSleep.sleeep(sleepSecond);
                    JSONObject jsonResult = JSONObject.fromObject(result);
                    if (jsonResult.getInt("code") != 1) {
                        System.out.println(userArray[1] + "," + userArray[2] + "," + Thread.currentThread() + bettingData);
                        System.out.println(result);
                    } else {
                        synchronized ("") {
                            count++;
                        }
                    }
                    //if (timeCount == 60) {
                    //    System.out.println("成功数据条数：" + (count));
                    //    timeCount = 0;
                    //    count = 0;
                    //}
                }
            }).start();
        }
    }


}
