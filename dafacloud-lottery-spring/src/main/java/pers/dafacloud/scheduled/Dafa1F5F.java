package pers.dafacloud.scheduled;

import net.sf.json.JSONArray;
import org.apache.http.Header;
import org.apache.ibatis.session.SqlSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import pers.dafacloud.dao.SqlSessionFactoryUtils;
import pers.dafacloud.mapper.betRecord.BetRecordMapper;
import pers.dafacloud.model.GetBetInfo;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.listUtils.ListSplit;
import pers.utils.urlUtils.UrlBuilder;

import java.util.*;
import java.util.concurrent.*;

//@Service
public class Dafa1F5F implements SchedulingConfigurer {
    private static String host = "http://52.76.195.164:8020";
    private static String addBettingUrl = host + "/v1/betting/addBetting";
    //private static String loginUrl = host + "/v1/users/login";
    //private static ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);
    private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("dev");
    private static BetRecordMapper betRecordMapper = sqlSession.getMapper(BetRecordMapper.class);
    private static List<String> betUser =
            FileUtil.readFile(Dafa1F5F.class.getResourceAsStream("/user.txt"));
    private static List<List<GetBetInfo>> listnew1fen = new ArrayList<>();
    private static List<List<GetBetInfo>> listnew5fen = new ArrayList<>();
    //"1407大发快三","1008大发时时彩","1300大发六合彩","1304大发PK10",
    //"1412 5分快3","1009 5分时时彩","1305 5分六合彩","1306 5分PK10"
    //static String[] lotteryCodes1f = {"1407", "1008", "1300", "1304", "1412", "1009", "1305", "1306"};
    //private static String[] lotteryCodes1f = {"1407", "1008" ,"1300", "1304"};
    //private static String[] lotteryCodes5f = {"1412", "1009","1305", "1306"};
    private static String[] lotteryCodes5f = {"1305"};
    private static List<List<String>> listsSplitUser;

    static {
        //for (String lotteryCode : lotteryCodes1f) {
        //    Map map=new HashMap();
        //    map.put("lotteryCode",lotteryCode);
        //    List<GetBetInfo> list = betRecordMapper.getRecordByLotteryCode(map);
        //    System.out.println(lotteryCode+","+list.size());
        //    listnew1fen.add(list);
        //}
        for (String lotteryCode : lotteryCodes5f) {
            Map map=new HashMap();
            map.put("lotteryCode",lotteryCode);
            List<GetBetInfo> list = betRecordMapper.getRecordByLotteryCode(map);
            System.out.println(lotteryCode+",长度"+list.size());
            listnew5fen.add(list);
        }
        listsSplitUser = ListSplit.split(betUser, 1);
    }

    //@Scheduled(cron = "55 * * * * * ")
    //public void a1(){
    //    try{ TimeUnit.SECONDS.sleep(10); }catch (Exception e){}
    //    System.out.println(Thread.currentThread().getName() + "a1 done");
    //}
    //
    //@Scheduled(cron = "56 * * * * * ")
    //public void a2(){
    //    try{ TimeUnit.SECONDS.sleep(4); }catch (Exception e){}
    //    System.out.println(Thread.currentThread().getName() + "a2 done");
    //}

    //1分系列
    //@Scheduled(cron = "5 * * * * * ")
    public void a() {
        Calendar now = Calendar.getInstance();
        int m = now.get(Calendar.MONTH);
        int d = now.get(Calendar.DAY_OF_MONTH);
        int h = now.get(Calendar.HOUR_OF_DAY);
        int mm = now.get(Calendar.MINUTE);
        String issueBefor = String.format("2019%02d%02d%04d", m, d, h * 60 + mm + 1);
        String issueCurrent = String.format("2019%02d%02d%04d", m + 1, d, h * 60 + mm + 1);
        System.out.println("1issueBefor:" + issueBefor + ",issueCurrent:" + issueCurrent);
        //List<GetBetInfo> list = betRecordMapper.getRecordByIssue("201907160701");//获取投注内容
        //System.out.println("当期数据量：" + list.size());
        if (listnew1fen.size() != 0)
            splitTask(listsSplitUser.get(0), listnew1fen, issueCurrent, "1");
    }

    //5分系列
    @Scheduled(cron = "6 * * * * * ")
    public void aa() {
        Calendar now = Calendar.getInstance();
        int m = now.get(Calendar.MONTH);
        int d = now.get(Calendar.DAY_OF_MONTH);
        int h = now.get(Calendar.HOUR_OF_DAY);
        int mm = now.get(Calendar.MINUTE);
        String issueBefor = String.format("2019%02d%02d%04d", m, d, h * 60 + mm + 1);
        String issueCurrent = String.format("2019%02d%02d%03d", m + 1, d, h * 12 + (mm / 5) + 1);
        System.out.println("2issueBefor:" + issueBefor + ",issueCurrent:" + issueCurrent);
        //List<GetBetInfo> list = betRecordMapper.getRecordByIssue("201907160701");//获取投注内容
        //System.out.println("当期数据量：" + list.size());
        if (listnew5fen.size() != 0)
            splitTask(listsSplitUser.get(1), listnew5fen, issueCurrent, "2");
    }

    public static void splitTask(List<String> users1, List<List<GetBetInfo>> listnew, String issueCurrent, String from) {
        //CountDownLatch cdl = new CountDownLatch(listnew.size());
        System.out.println("线程数：" + listnew.size());
        for (int i = 0; i < listnew.size(); i++) { //
            int attri = i;
            excutors.execute(() -> {
                Collections.shuffle(listnew.get(attri));
                bet(users1.get(attri), listnew.get(attri).subList(0, 10), issueCurrent);
                //cdl.countDown();
            });
        }
        try {
            //cdl.await();
        } catch (Exception e) {
        }
        System.out.println("------------" + (from));
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
        for (GetBetInfo betInfo : getBetInfos) {
            String lotteryCode = betInfo.getLotteryCode();
            String point = lotteryCode.equals("1407") || lotteryCode.equals("1412") ? "8" :
                    lotteryCode.equals("1008") || lotteryCode.equals("1009") ? "8" :
                            lotteryCode.equals("1300") || lotteryCode.equals("1305") ? "10" : "8";

            //String issue = lotteryCode.equals("1407") || lotteryCode.equals("1412") || lotteryCode.equals("1407") || lotteryCode.equals("1412")
            //        ? issueCurrent : "8";
            JsonObjectBuilder order1 = JsonObjectBuilder.custom()
                    .put("lotteryCode", betInfo.getLotteryCode())
                    .put("playDetailCode", betInfo.getPlayDetailCode())
                    .put("bettingNumber", betInfo.getBettingNumber())
                    .put("bettingAmount", betInfo.getBettingAmount())
                    .put("bettingCount", betInfo.getBettingCount())
                    .put("bettingPoint", point)
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
                System.out.println(lotteryCode + "," + userArray[1] + "," + userArray[2] + "," + result);
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("======" + e);
            }
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(2));
    }
}
