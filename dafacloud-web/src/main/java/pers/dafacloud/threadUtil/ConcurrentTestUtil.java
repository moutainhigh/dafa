package pers.dafacloud.threadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentTestUtil {

    /**
     * 多线程并发执行某项任务
     *
     * @param concurrentThreads    并发线程数，可以用来模拟并发访问用户数
     * @param times                总共执行多少次
     * @param task                 任务
     * @param requestHandler       结果处理器
     * @param executeTimeoutMillis 执行任务总超时
     * @throws InterruptedException
     * @throws ExecutionException
     */

    public static <T> void concurrentTest(long concurrentThreads, int times, final Callable<T> task,
                                          RequestHandler<T> requestHandler, long executeTimeoutMillis)
            throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool((int) concurrentThreads);//线程数在这里设定

        List<Future<T>> results = new ArrayList<Future<T>>(times);
        //开始时间
        long startTimeMillis = System.currentTimeMillis();



        //times执行多少次。10个执行30次，每个执行3次？，并发，线程集合起来一起同时执行
        for (int i = 0; i < times; i++) {
            results.add(executor.submit(task));
            //System.out.println(task);
            executor.submit(task);
            //单个线程顺序执行
            /*if (requestHandler != null) {
                requestHandler.handle(executor.submit(task).get());
            }*/

        }       


        executor.shutdown();//关闭

        boolean executeCompleteWithinTimeout = executor.awaitTermination(executeTimeoutMillis, TimeUnit.MILLISECONDS);

        if (!executeCompleteWithinTimeout) {
            System.out.println("Execute tasks out of timeout [" + executeTimeoutMillis + "ms]");

            /*
             * 取消所有任务
             */
            for (Future<T> r : results) {
                r.cancel(true);
            }
        } else {
            long totalCostTimeMillis = System.currentTimeMillis() - startTimeMillis;

            // 线程池此时肯定已关闭，处理任务结果
            for (Future<T> r : results) {
                if (requestHandler != null) {
                    requestHandler.handle(r.get());
                }
            }

            System.out.println("线程数concurrent threads: " + concurrentThreads + ", 执行次数times: " + times);
            System.out.println("total cost time(ms): " + totalCostTimeMillis + "ms, avg time(ms): " + ((double) totalCostTimeMillis / times));
            System.out.println("tps: " + (double) (times * 1000) / totalCostTimeMillis);
        }

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /*
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
        */
        ConcurrentTestUtil.concurrentTest(10, 250,
                //task任务
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        long startTimeMillis = System.currentTimeMillis(); 
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        long totalCostTimeMillis = System.currentTimeMillis() - startTimeMillis;
                        /*for (int i = 0; i < list.size(); i++) {
                            Thread.currentThread().setName();
                            System.out.println("测试");
                        }*/
                        if ("pool-1-thread-1".equals(Thread.currentThread().getName())){
                            Thread.sleep(2000);
                        }


                        System.out.println(Thread.currentThread().getName()+","+totalCostTimeMillis);


                        //String mobile=httpsUtils.doGetForHttps("http接口", "参数Map", "UTF-8");//测试接口

                        return "ok";
                    }
                },
                new RequestHandler<String>() {
                    @Override
                    public void handle(String result) {
                        System.out.println("result: " + result);
                    }
                }, 600000);
    }

}
