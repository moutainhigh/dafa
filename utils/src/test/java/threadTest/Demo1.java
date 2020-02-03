package threadTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Demo1 {
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
        List<Object> result = handlerLogin();
        handlerBet(result);
    }

    private static void handlerBet(List<Object> result) {
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < result.size(); i++) {
            executorService.execute(() -> {
                try {
                    bet(latch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            latch.await(); //等待子线程执行完
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private static List<Object> handlerLogin() {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    login(latch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        try {
            latch.await(); //等待子线程执行完


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void login(CountDownLatch latch) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
        System.out.println("login");
    }

    public static void bet(CountDownLatch latch) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
        System.out.println("bet");
    }
}
