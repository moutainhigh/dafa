package pers.dafacloud.threadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyExecutor {

    public static void main(String[] args) {
            test();
    }

    public static<T> void test(){
        ExecutorService executor = Executors.newFixedThreadPool(5);//线程数在这里设定

        //List<Future<T>> results = new ArrayList<>(100);

        //开始时间
        long startTimeMillis = System.currentTimeMillis();

        List<String> list =new ArrayList<>();
        list.add("cookie1");
        list.add("cookie2");
        list.add("cookie3");
        list.add("cookie4");
        list.add("cookie5");
        list.add("cookie6");
        list.add("cookie7");
        list.add("cookie8");
        list.add("cookie9");
        list.add("cookie10");

        for (int i = 0; i < list.size(); i++) {
            MyCallable mc = new MyCallable(list.get(i));
            System.out.println("list:"+i);
            executor.submit(mc);
        }

        //times执行多少次。10个执行30次，每个执行3次？，并发，线程集合起来一起同时执行
        for (int i = 0; i < 100; i++) {
            //results.add(executor.submit(mc));
            //System.out.println(task);
            //executor.submit(task);
            //单个线程顺序执行
            /*if (requestHandler != null) {
                requestHandler.handle(executor.submit(task).get());
            }*/

        }

    }


}
