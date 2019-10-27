package pers.dafacloud;

import net.sf.json.JSONArray;
import org.apache.http.Header;
import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.getBetInfo.GetBetInfoMapper;
import pers.dafacloud.Dao.pojo.GetBetInfo;
import pers.dafacloud.page.pageLogin.Login;
import pers.utils.ThreadSleep.ThreadSleep;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class LHCshalv {

    private static String host = "http://dafacloud-pre.com";
    private static String addBettingUrl = host + "/v1/betting/addBetting";
    private static String loginUrl = host + "/v1/users/login";

    public static void main(String[] args) {
        //SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("dev");
        //GetBetInfoMapper getBetInfoMapper = sqlSession.getMapper(GetBetInfoMapper.class);
        //List<GetBetInfo> betContents = getBetInfoMapper.getRecordByIssue("201907130038");
        //System.out.println(betContents.size());

        //betting();

        //service.scheduleWithFixedDelay(()->{
        //    System.out.println(System.currentTimeMillis());
        //},5,60, TimeUnit.SECONDS);

    }


    public static void bet(List<String> betContents){

    }



    public static void betting(List<String> users1, List<String> betContents1, int sleepSecond1) {
        for (int i = 0; i < users1.size(); i++) { //用户
            List<String> betContents = betContents1;
            List<String> users = users1;
            int index = i;
            int sleepSecond = sleepSecond1;
            new Thread(() -> {
                //String[] userArray = users.get(index).split(",");

                //System.out.println(users.get(0));
                Header[] headers = HttpHeader.custom()
                        .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                        //.other("x-user-id", userArray[0])
                        //.other("x-tenant-code", userArray[1])
                        //.other("x-user-name", userArray[2])
                        //.other("x-source-Id", "1")
                        //.other("Origin", "http://52.76.195.164")
                        .build();

                String body = Login.getLoginBody(users.get(index), "123456");
                System.out.println(body);
                //HttpConfig httpConfig = HttpConfig.custom().url(url).body(bettingData).headers(headers);
                HttpCookies httpCookies = HttpCookies.custom();
                HttpConfig httpConfig = HttpConfig.custom().url(loginUrl).body(body).headers(headers).context(httpCookies.getContext());
                //HttpCookies
                String result0 = DafaRequest.post(httpConfig);
                System.out.println(users.get(index)+"，"+result0);
                //投注
                for (int j = 0; j < 1; j++) {
                    int betContentIndex = (int) (Math.random() * (betContents.size()));
                    String betContent = betContents.get(betContentIndex);
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
                            .put("bettingPoint", "5")
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
                    httpConfig = httpConfig.url(addBettingUrl).body(bettingData);
                    try {
                        //result = DafaRequest.post(url, bettingData, headers);
                        result = DafaRequest.post(httpConfig);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    System.out.println(betContentIndex + "," + bettingData);
                    System.out.println(users.get(index) + "," + result);
                    System.out.println("----------------------------");
                    ThreadSleep.sleeep(sleepSecond);
                    //JSONObject jsonResult = JSONObject.fromObject(result);
                    //if (jsonResult.getInt("code") != 1) {
                    //    System.out.println(userArray[2] + "," + Thread.currentThread() + bettingData);
                    //    System.out.println(result);
                    //}
                }
            }).start();
        }
    }

}
