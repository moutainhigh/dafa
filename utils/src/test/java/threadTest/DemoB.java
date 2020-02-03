package threadTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DemoB {
    private final static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);


    public static void main(String[] args) {


         CyclicBarrier cyclicBarrier =new CyclicBarrier(10);
        for (int i = 0; i < 10; i++) {

            executorService.execute(()->{
                login();
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bet();
            });

            //new Thread(() -> {
            //    login();
            //    try {
            //        cyclicBarrier.await();
            //    } catch (Exception e) {
            //        e.printStackTrace();
            //    }
            //    bet();
            //}).start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //cyclicBarrier.reset();

    }


    public static void login() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("login");
    }

    public static void bet() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //latch.countDown();
        }
        System.out.println("bet");
    }
}
