package pers.dafacloud.beting;

import java.util.Timer;
import java.util.TimerTask;

public class InitializaIssue {
//	private static Timer timer = new Timer();
	public static void main(String[] args) {
		GetIssue.cTime();
		endTimeTask();
	}

	public static void endTimeTask() {
		Timer timerOne = new Timer();
		timerOne.schedule(new TimerTask() {
			@Override
			public void run() {
				GetIssue.endTimeOne--;
				while (GetIssue.endTimeOne == 0) {
					GetIssue.endTimeOne=60;
					GetIssue.issueOne++;
				}
				long s = GetIssue.endTimeOne % 60;
				System.out.println("1分倒计时："  + s);
				System.out.println("1分期号：" + GetIssue.issueOne);
			}
		}, 0, 1000);

		Timer timerFive = new Timer();
		timerFive.schedule(new TimerTask() {
			@Override
			public void run() {
				GetIssue.endTimeFive--;
				while (GetIssue.endTimeFive == 0) {
					GetIssue.endTimeFive = 50*60;
					GetIssue.issueFive++;
				}
				long f = GetIssue.endTimeFive / 60;
				long s = GetIssue.endTimeFive % 60;
				System.out.println("5分倒计时：" + f + ":" + s);
				System.out.println("1分期号：" + GetIssue.issueFive);
			}
		}, 0, 1000);
	}
}
