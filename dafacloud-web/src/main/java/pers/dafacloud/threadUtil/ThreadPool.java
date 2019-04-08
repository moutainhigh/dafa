package pers.dafacloud.threadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPool {


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //创建线程池对象

        ExecutorService threadPool = Executors.newFixedThreadPool(5);//达到n个线程执行任务


        //创建一个Callable接口子类对象

        //MyCallable c = new MyCallable();

        MyCallable c = new MyCallable("");//一个人对应一个线程

        MyCallable c2 = new MyCallable("");


        List<String> list =new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");


        MyCallable cc = new MyCallable("");
        for (int i = 0; i < 100; i++) {
            threadPool.submit(c);
        }
        
        
        //任务是一个，线程多个，执行多次
        /*for (int i = 0; i <100 ; i++) {
            for (int j = 0; j < list.size(); j++) {
                MyCallable cc = new MyCallable(100, 200);
                Future<String> result1 = threadPool.submit(c);
                String sum = result1.get();
                System.out.println(sum+",");
            }
        }*/

        //获取线程池中的线程，调用Callable接口子类对象中的call()方法, 完成求和操作

        //<Integer> Future<Integer> submit(Callable<Integer> task)

        // Future 结果对象

        Future<String> result = threadPool.submit(c);

        //此 Future 的 get 方法所返回的结果类型

        String sum = result.get();

        System.out.println("sum=" + sum);


//再演示

        result = threadPool.submit(c2);

        sum = result.get();

        System.out.println("sum=" + sum);

//关闭线程池(可以不关闭)


    }

}


