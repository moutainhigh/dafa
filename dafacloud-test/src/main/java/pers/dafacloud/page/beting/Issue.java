package pers.dafacloud.page.beting;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.dafacloud.page.pageLogin.Login;
import pers.dafacloud.utils.common.Time;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Issue {

    private final static Logger Log = LoggerFactory.getLogger(InitializaIssueEndtime.class);
    static Path getServerTimeMillisecondPath = Path.getServerTimeMillisecond;

    private int endTime = 0;
    private long issue = 0;

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public long getIssue() {
        return issue;
    }

    public void setIssue(long issue) {
        this.issue = issue;
    }

    /**
     * 获取服务器时间
     */
    public long getServerTimeMillisecond() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        String result = Request.doGet(getServerTimeMillisecondPath.value, headers);
        if (!result.contains("成功")) {
            Log.info("获取服务器时间失败：" + result);
        }
        long l = JSONObject.fromObject(result).getLong("data");
        return l;
    }

    public void getPublicLottery(String LotteryCode) {
        String url = Path.openTime.value + "lotteryCode=" + LotteryCode;
        String r = Request.doGet(url);
        JSONArray ja = JSONObject.fromObject(r).getJSONArray("data");
        String currentDate = Time.YYYYgMMgDD();
        long currentMillTime = getServerTimeMillisecond();
        for (int i = 0; i < ja.size(); i++) {
            //System.out.println(ja.get(i));
            JSONObject job = ja.getJSONObject(i);
            String startTime = job.getString("startTime");
            String endTime = job.getString("endTime");

            //1。转日期，在相减
            String startDate = currentDate + " " + startTime.substring(11);
            String endDate = currentDate + " " + endTime.substring(11);
            long diffStartDate = currentMillTime - Time.getMillSecond(startDate);
            long diffEndDate = currentMillTime - Time.getMillSecond(endDate);
            if (diffStartDate < 0 || diffEndDate < 0) {
                System.out.println(diffStartDate + "," + diffEndDate);
                String issue = job.getString("issue");
                long endSecond = Math.abs(diffEndDate / 1000);
                System.out.println("剩余秒数:" + endSecond);
                System.out.println("剩余分:" + endSecond / 60);
                System.out.println("剩余秒:" + endSecond % 60);
                System.out.println(issue);
                break;
            }
            //2。比较时分秒
        }


    }

    public static void main(String[] args) {

        Login login = new Login();
        login.loginDafaCloud("duke01", "123456");


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            Issue issue = new Issue();
//            issue.getPublicLottery("1040");
            @Override
            public void run() {
                //endSecond--;
//                System.out.println(s);
                while (InitializaIssueEndtime.endTimeFive == 0) {
                    InitializaIssueEndtime.endTimeFive = 50 * 60;
                    InitializaIssueEndtime.issueFive++;
                }
                long f = InitializaIssueEndtime.endTimeFive / 60;
                long s = InitializaIssueEndtime.endTimeFive % 60;
                System.out.println("5分倒计时：" + f + ":" + s);
                System.out.println("1分期号：" + InitializaIssueEndtime.issueFive);
            }
        }, 0, 1000);
        timer.cancel();
    }

}
