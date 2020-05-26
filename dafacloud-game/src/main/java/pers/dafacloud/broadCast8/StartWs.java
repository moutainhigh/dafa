package pers.dafacloud.broadCast8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class StartWs {

    //private static String host = Constants.host;
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);
    //static int count;

    public static void main(String[] args) {
        //List<String> user = FileUtil.readFile(StartWs.class.getResourceAsStream("/users.txt"));
        task(Integer.parseInt(args[0]));
        //task(200);
        //for (int i = 0; i < 20000; i++) {
        //    System.out.println(i);
        //    new Thread(()->{
        //        //for (;;) {
        //        //    try {
        //        //        Thread.sleep(1);
        //        //    } catch (InterruptedException e) {
        //        //        e.printStackTrace();
        //        //    }
        //        //}
        //    }).start();
        //}
    }

    public static void task(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(i);
            try {
                excutors.execute(SendMessageSX::process);//等价于excutors.execute(() -> sendMessageSX.process());
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("完成");
    }
}
