package threadTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DemoA {
    private final static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(100);

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            executorService.execute(()->{
                login(latch);
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bet(latch);
            });
            //new Thread(() -> {
            //    login(latch);
            //    try {
            //        latch.await();
            //    } catch (InterruptedException e) {
            //        e.printStackTrace();
            //    }
            //    bet(latch);
            //}).start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        latch.countDown();
        executorService.shutdown();
        System.out.println("executorService.shutdown()");

    }


    public static void login(CountDownLatch latch) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("login");
    }

    public static void bet(CountDownLatch latch) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //latch.countDown();
        }
        System.out.println("bet");
    }
}
