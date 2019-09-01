package pers.testShaLv;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TestThread {

    private static int count = 0;

    public static void main(String[] args) {
        Timer timerOne = new Timer();
        timerOne.schedule(new TimerTask() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                System.out.println(calendar.get(calendar.SECOND));
                if (calendar.get(calendar.SECOND) == 59) {
                    System.out.println(Thread.currentThread() + "," + (count));
                    //timeCount = 0;
                }
            }
        }, 0, 1000);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 10000000; j++) {
                        synchronized ("") {
                            count++;
                        }
                        TimeUnit.SECONDS.sleep(1);//也是会抛异常
                    }
                } catch (Exception e) {
                }

            }).start();
        }

    }
}
