package pers.dafacloud.scheduled;

import net.sf.json.JSONArray;
import org.apache.http.Header;
import org.apache.ibatis.session.SqlSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pers.dafacloud.dao.SqlSessionFactoryUtils;
import pers.dafacloud.dao.mapper.betRecord.BetRecordMapper;
import pers.dafacloud.dao.pojo.GetBetInfo;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.listUtils.ListSplit;
import pers.utils.urlUtils.UrlBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Service
public class dafaLHC {
    private static String host = "http://52.76.195.164:8020";
    private static String addBettingUrl = host + "/v1/betting/addBetting";
    //private static String loginUrl = host + "/v1/users/login";
    //private static ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("dev");
    private static BetRecordMapper betRecordMapper = sqlSession.getMapper(BetRecordMapper.class);
    private static List<String> betUser =
            FileUtil.readFile(dafaLHC.class.getResourceAsStream("/user.txt"));

    @Scheduled(cron = "5 * * * * * ")
    public void aa() {
        Calendar now = Calendar.getInstance();
        int m = now.get(Calendar.MONTH);
        int d = now.get(Calendar.DAY_OF_MONTH);
        int h = now.get(Calendar.HOUR_OF_DAY);
        int mm = now.get(Calendar.MINUTE);
        String issueBefor = String.format("2019%02d%02d%04d", m, d, h * 60 + mm+ 1);
        String issueCurrent = String.format("2019%02d%02d%04d", m + 1, d, h * 60 + mm + 1);
        System.out.println("issueBefor:"+issueBefor+",issueCurrent:"+issueCurrent);
        List<GetBetInfo> list = betRecordMapper.getRecordByIssue("201907160701");
        System.out.println("当期数据量：" + list.size());
        if (list.size() != 0)
            splitTask(betUser, list, issueCurrent);
    }

    public static void splitTask(List<String> users1, List<GetBetInfo> betContents, String issueCurrent) {
        if (betContents != null) {
            List<List<GetBetInfo>> listnew = ListSplit.split(betContents, 20);//每个子list长度20，每个用户投注20单
            CountDownLatch cdl = new CountDownLatch(listnew.size());
            System.out.println("线程数：" + listnew.size());
            for (int i = 0; i < listnew.size(); i++) {
                int attri = i;
                excutors.execute(() -> {
                    bet(users1.get(attri), listnew.get(attri), issueCurrent);
                    cdl.countDown();
                });
            }
            try {
                cdl.await();
            } catch (Exception e) {
            }
            System.out.println();
        }
    }

    public static void bet(String users, List<GetBetInfo> getBetInfos, String issueCurrent) {
        String[] userArray = users.split(",");
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-tenant-code", userArray[0])
                .other("x-user-id", userArray[1])
                .other("x-user-name", userArray[2])
                .other("x-source-Id", "1")
                .other("Origin", "http://52.76.195.164")
                .build();
        long now = System.currentTimeMillis();
        long lcMillTime = 0;

        for (GetBetInfo betInfo : getBetInfos) {
            //String currentDate = "";
            //try {
            //    SimpleDateFormat sdfOne = new SimpleDateFormat("yyyyMMdd");
            //    lcMillTime = sdfOne.parse(sdfOne.format(now)).getTime();
            //    Date date = new Date();
            //    currentDate = sdfOne.format(date);
            //    //System.out.println(currentDate);
            //} catch (Exception e) {
            //    e.printStackTrace();
            //}
            //int second = (int) (now - lcMillTime) / 1000;//距离今日凌晨秒数
            //int issueOneNum = second / 60 + 1;//期数

            JsonObjectBuilder order1 = JsonObjectBuilder.custom()
                    .put("lotteryCode", betInfo.getLotteryCode())
                    .put("playDetailCode", betInfo.getPlayDetailCode())
                    .put("bettingNumber", betInfo.getBettingNumber())
                    .put("bettingAmount", betInfo.getBettingAmount())
                    .put("bettingCount", betInfo.getBettingCount())
                    .put("bettingPoint", "10")
                    //.put("bettingIssue", String.format("%s%04d", currentDate, issueOneNum))
                    .put("bettingIssue", issueCurrent)
                    .put("graduationCount", betInfo.getGraduationCount())
                    .put("bettingUnit", betInfo.getBettingUnit());

            JSONArray orders = JsonArrayBuilder
                    .custom()
                    .addObject(order1.bulid())
                    .bulid();
            String bettingData = UrlBuilder.custom().addBuilder("bettingData", orders.toString()).fullBody();
            String result = "";
            HttpConfig httpConfig = HttpConfig.custom().url(addBettingUrl).body(bettingData).headers(headers);
            try {
                result = DafaRequest.post(httpConfig);
                System.out.println(userArray[1] + "," + userArray[2] + "," + result);
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("======" + e);
            }
        }
    }

    public String getIssue() {
        return null;
    }

}
