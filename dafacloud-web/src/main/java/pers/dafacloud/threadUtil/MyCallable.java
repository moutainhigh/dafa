package pers.dafacloud.threadUtil;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    //成员变量

    private String cookie;

    private MyCallable(){

    }

    public  MyCallable(String cookie){

        this.cookie = cookie;

    }

    @Override
    public String call() throws Exception {
        for (int i = 0; i < 5; i++) {
            long startTimeMillis = System.currentTimeMillis();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if ("cookie1".equals(this.cookie)){
                Thread.sleep(5000);
            }
            long totalCostTimeMillis = System.currentTimeMillis() - startTimeMillis;
            System.out.println(Thread.currentThread().getName()+","+totalCostTimeMillis+","+this.cookie+","+i);
        }
        return "请求成功";

    }


}
