package pers.dafacloud.page.beting;

import java.util.Timer;
import java.util.TimerTask;

public class StartTimeTask {



    public static void endTimeTask() {
        Timer timerOne = new Timer();
        timerOne.schedule(new TimerTask() {
            @Override
            public void run() {
                InitializaIssueEndtime.endTimeOne--;
                while (InitializaIssueEndtime.endTimeOne == 0) {
                    InitializaIssueEndtime.endTimeOne = 60;//
                    InitializaIssueEndtime.issueOne++; //期号++
                }
                long s = InitializaIssueEndtime.endTimeOne % 60;
                System.out.println("1分倒计时：" + s);
                System.out.println("1分期号：" + InitializaIssueEndtime.issueOne);
            }
        }, 0, 1000);

        Timer timerFive = new Timer();
        timerFive.schedule(new TimerTask() {
            @Override
            public void run() {
                InitializaIssueEndtime.endTimeFive--;
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

    }
}
