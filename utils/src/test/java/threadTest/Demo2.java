package threadTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Demo2 {
    private final static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        //new Thread(() -> {
        //    try {
        //        Thread.sleep(10000);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //    System.out.println("并发测试");
        //});
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
        }
        latch.countDown();

    }




    public static void login(CountDownLatch latch) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //latch.countDown();
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
