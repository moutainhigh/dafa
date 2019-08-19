package pers.dafacloud.page.beting;

import java.text.SimpleDateFormat;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.dafacloud.utils.common.Time;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import pers.dafacloud.page.pageLogin.Login;

/**
 * 奖期 和 倒计时
 */
public class InitializaIssueEndtime {

    private final static Logger Log = LoggerFactory.getLogger(InitializaIssueEndtime.class);
    static Path getServerTimeMillisecondPath = Path.getServerTimeMillisecond;

    public static int endTimeOne = 0;
    public static long issueOne = 0;

    public static int endTimeFive = 0;
    public static long issueFive = 0;

    public static int endTimePublic = 0;
    public static String issuePublic = "";
    public static int step = 0;
    public static JSONArray plan = null;

    public static long serverTimeMilliSecond = 0;

    /**
     * 获取服务器时间
     */
    public static long getServerTimeMillisecond() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        String result = Request.doGet(getServerTimeMillisecondPath.value, headers);
        if (!result.contains("成功")) {
            Log.info("获取服务器时间失败：" + result);
        }
        long l = JSONObject.fromObject(result).getLong("data");
        return l;
    }

    /**
     * 私彩初始化奖期和倒计时
     */
    public static void executeInitializa() {
        long currentMillTime = getServerTimeMillisecond();//
        long now = System.currentTimeMillis();
        long lcMillTime = 0;
        String currentDate = "";
        try {
            SimpleDateFormat sdfOne = new SimpleDateFormat("yyyyMMdd");
            lcMillTime = sdfOne.parse(sdfOne.format(now)).getTime();
            Date date = new Date();
            currentDate = sdfOne.format(date);
            System.out.println(currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int second = (int) (currentMillTime - lcMillTime) / 1000;//距离今日凌晨秒数
        int s = second % 60;//秒
        int m = second / 60 % 60;//分
        int h = second / (60 * 60);//时
        System.out.println("距离今日凌晨秒数：" + second);
        System.out.println("当前时间：" + h + ":" + m + ":" + s);

        //1分彩
        endTimeOne = 60 - s;//倒计时秒数
        int issueOneNum = second / 60 + 1;//期数
        //issueOne = currentDate + String.format("%04d",issueOneNum);
        issueOne = Long.parseLong(currentDate + String.format("%04d", issueOneNum));
        System.out.println("1分彩倒计时:" + endTimeOne);
        System.out.println("1分彩当前期数：" + issueOne);

        //5分彩
        endTimeFive = 5 * 60 - second % (5 * 60) - 1;//倒计时秒数
        issueFive = Long.parseLong(currentDate + String.format("%03d", second / (5 * 60) + 1));//期数
        System.out.println("5分彩当前期数：" + issueFive);

        int endFiveM = 5 - 1 - endTimeFive / 60;
        int endFives = 60 - endTimeFive % 60;
        System.out.println("5分彩倒计时秒数:" + endTimeFive);
        System.out.println("5分彩倒计时:" + endFiveM + ":" + endFives);
    }

    /**
     * 官彩奖期获取
     * */
    public static void getPublicLottery(String LotteryCode) {
        String url = Path.openTime.value + "lotteryCode=" + LotteryCode;
        String r = Request.doGet(url);
        try {
            plan = JSONObject.fromObject(r).getJSONArray("data");//开奖区间
        } catch (Exception e) {
            System.out.println(r);
            e.printStackTrace();
            return;
        }

        String currentDate = Time.YYYYgMMgDD();
        serverTimeMilliSecond = getServerTimeMillisecond();
        for (int i = 0; i < plan.size(); i++) {
            //System.out.println(ja.get(i));
            JSONObject job = plan.getJSONObject(i);
            String startTime = job.getString("startTime");
            String endTime = job.getString("endTime");

            //1。转毫秒和服务器时间相减
            String startDate = currentDate + " " + startTime.substring(11);//去掉年月日，加上当前年月日
            String endDate = currentDate + " " + endTime.substring(11);
            long startMillSecond = Time.getMillSecond(startDate);
            long endMillSecond = Time.getMillSecond(endDate);
            long diffStartDate = serverTimeMilliSecond - startMillSecond;
            long diffEndDate = serverTimeMilliSecond - endMillSecond;
            /*System.out.println("11:" + serverTimeMilliSecond);
            System.out.println("22:" + endMillSecond);*/
            if (diffEndDate < 0 && diffStartDate > 0) {
                //1.将001转20190501001这样再++得出下一期期号
                //issuePublic = Long.parseLong(Time.YYYYMMDD() + job.getString("issue"));
                issuePublic = job.getString("issue");
                //2.获取001，以及长度；将001转int++，得出下一期期号，和当前时间拼接
                String issueFromPlan = job.getString("issue");
                int issueLenth = issueFromPlan.length();

                endTimePublic = (int) Math.abs(diffEndDate / 1000);
                long sleepTime = Math.abs(diffEndDate % 1000);
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                serverTimeMilliSecond = serverTimeMilliSecond + sleepTime;
                System.out.println("服务器时间毫秒：" + serverTimeMilliSecond);
                System.out.println("剩余秒数:" + endTimePublic);
                System.out.println("剩余分:" + endTimePublic / 60);
                System.out.println("剩余秒:" + endTimePublic % 60);
                System.out.println(issuePublic);
                break;
            }else {//预售期间
                issuePublic="001";
                endTimePublic = (int) Math.abs((serverTimeMilliSecond-
                        Time.getMillSecond(currentDate + " " + plan.getJSONObject(0).getString("endTime").substring(11)))/ 1000);
            }

            //2。比较时分秒
        }
        /*
        推荐
        一。1.倒计时结束 ，下一期加一，2.如果当前期是最后一起，下一期期号，就是第一期，倒计时就是预售时间 3.根据期号，获取下一期间隔再-1作为倒计时，
            1.倒计时=0，定时任务走完一秒，计算下一期，得出倒计时60，赋值60秒，定时器将在60的基础上减1，
        二。1.倒计时结束，再获取下一期，
        * */
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endTimePublic--;//倒计时
                if (endTimePublic == 0) {
                    int issueLength = issuePublic.length();
                    int issueInt = Integer.parseInt(issuePublic);//int类型期号
                    if (issueInt == plan.size()) {//最后一期,设置成第一期，不用再加1
                        issueInt = 1;
                        //第一期
                        issuePublic = String.format("%0" + issueLength + "d", issueInt);
                        //预售期间
                        endTimePublic = (int) Math.abs((serverTimeMilliSecond-
                                Time.getMillSecond(Time.YYYYgMMgDD() + " "
                                        + plan.getJSONObject(0).getString("endTime").substring(11)))/ 1000);
                        return;
                    }
                    //issuePublic  = String.valueOf(issueInt++);
                    issuePublic = String.format("%0" + issueLength + "d", issueInt + 1);
                    System.out.println("新期号:" + issuePublic);
                    //倒计时
                    JSONObject job = plan.getJSONObject(issueInt);//坐标减1
                    String startTime = job.getString("startTime");
                    String endTime = job.getString("endTime");
                    long endMillSecond = Time.getMillSecond(endTime);
                    long startMillSecond = Time.getMillSecond(startTime);
                    step = (int) ((endMillSecond - startMillSecond) / 1000);//区间间隔秒数，理论上不减1
                    endTimePublic = step;
                }

                long f = endTimePublic / 60;
                long s = endTimePublic % 60;
                System.out.println("倒计时：" + f + ":" + s);
                System.out.println("期号：" + issuePublic);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");//设置日期格式
                System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

                /*serverTimeMilliSecond = serverTimeMilliSecond + 1000;//服务器时间
                while (endTimePublic == 0) {
                    serverTimeMilliSecond = getServerTimeMillisecond();
                    for (int i = 0; i < plan.size(); i++) { //计划
                        JSONObject j = plan.getJSONObject(i);
                        long endMilliSecond = Time.getMillSecond(Time.YYYYgMMgDD() + " " + j.getString("endTime").substring(11));
                        long startMilliSecond = Time.getMillSecond(Time.YYYYgMMgDD() + " " + j.getString("startTime").substring(11));
                        long diffEndMilliSecond = serverTimeMilliSecond - endMilliSecond;
                        if ( diffEndMilliSecond < 0 && diffEndMilliSecond < -1000) {//小于1秒，还在上一期且
                            System.out.println("当前服务器时间："+serverTimeMilliSecond);
                            System.out.println("结束时间"+endMilliSecond+"，开始时间"+startMilliSecond);
                            System.out.println("当前时间-结束时间/毫秒："+(serverTimeMilliSecond - endMilliSecond));
                            System.out.println("当前时间-开始时间/毫秒："+(serverTimeMilliSecond - startMilliSecond));
                            Long issuePublicNew = Long.parseLong(Time.YYYYMMDD() + j.getString("issue"));//期数
                            if (issuePublicNew==issuePublic) {
                                System.out.println("期数一样");
                                continue;
                            }
                            issuePublic=issuePublicNew;
                            endTimePublic = (int) Math.abs(diffEndMilliSecond / 1000)-1;
                            System.out.println("新期数："+j.getString("issue"));*/

                            /*String currentDate2 = Time.YYYYgMMgDD();
                            String startTime = j.getString("startTime");
                            String endTime = j.getString("endTime");
                            String startDate = currentDate2 + " " + startTime.substring(11);
                            String endDate = currentDate2 + " " + endTime.substring(11);
                            long endMillSecond = Time.getMillSecond(endDate);
                            long startMillSecond = Time.getMillSecond(startDate);
                            step = (int) ((endMillSecond - startMillSecond) / 1000) ;//区间间隔秒数*/
                // break;


            }
            //endTimePublic = step;//下一期的倒计时
				    /*
                    issuePublic++;//每一期间隔时间不一样，重新再获取下一期的投注间隔
                    for (int i = 0; i < plan.size(); i++) {
                        Long l = Long.parseLong(Time.YYYYMMDD() + plan.getJSONObject(i).getString("issue"));
                        if (l == issuePublic) {
                            JSONObject job = plan.getJSONObject(i);
                            String startTime = job.getString("startTime");
                            String endTime = job.getString("endTime");
                            //1。转日期，在相减
                            String currentDate2 = Time.YYYYgMMgDD();
                            String startDate = currentDate2 + " " + startTime.substring(11);
                            String endDate = currentDate2 + " " + endTime.substring(11);
                            long startMillSecond = Time.getMillSecond(startDate);
                            long endMillSecond = Time.getMillSecond(endDate);
                            step = (int) ((endMillSecond - startMillSecond) / 1000) - 1;//区间间隔秒数
                            break;
                        }

                    }*/


        }, 0, 1000);

    }

    public static void main(String[] args) {
        Login login = new Login();
        login.loginDafaCloud("duke02", "123456");
//		executeInitializa();
        getPublicLottery("1401");


    }


}
