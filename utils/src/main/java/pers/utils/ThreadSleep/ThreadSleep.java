package pers.utils.ThreadSleep;

public class ThreadSleep {

    public static void sleeep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
