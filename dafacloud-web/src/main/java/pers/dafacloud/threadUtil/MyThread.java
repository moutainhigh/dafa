package pers.dafacloud.threadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyThread implements Runnable{

    private String name;

    public MyThread(String name){
        this.name=name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 300; i++) {

            try {
                if ("thread-1".equals(this.name)){
                    Thread.sleep(2000);
                }else {
                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(this.name+" run....");
        }

    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        MyThread my = new MyThread("thread-1");
        MyThread my1 = new MyThread("thread-2");
        MyThread my2 = new MyThread("thread-3");
        MyThread my3 = new MyThread("thread-4");
        MyThread my4 = new MyThread("thread-5");
        MyThread my5 = new MyThread("thread-6");
        executor.execute(my);
        executor.execute(my1);
        executor.execute(my2);
        executor.execute(my3);
        executor.execute(my4);
        executor.execute(my5);


        System.out.println("主线程结束");
    }
}
