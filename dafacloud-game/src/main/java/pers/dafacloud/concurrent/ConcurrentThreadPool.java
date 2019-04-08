package pers.dafacloud.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentThreadPool implements IConcurrentThreadPool {
    private ThreadPoolExecutor threadPoolExecutor;
    // 核心线程数
    private int corePoolSize = 500;
    // 最大线程数
    private int maximumPoolSize = 2000;
    // 超时时间30秒
    private long keepAliveTime = 200;

    //初始化线程
    @Override
    public void initConcurrentThreadPool() {
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>());
    }

    //提交单个
    @Override
    public <V> V submit(CallableTemplate<V> task) throws InterruptedException,
            ExecutionException {
        Future<V> result = threadPoolExecutor.submit(task);

        return result.get();
    }

    //提交多个
    @Override
    public <V> List<V> invokeAll(List<? extends CallableTemplate<V>> tasks)
            throws InterruptedException, ExecutionException {

        List<Future<V>> tasksResult = threadPoolExecutor.invokeAll(tasks);//执行tasks
        List<V> resultList = new ArrayList<V>();

        for (Future<V> future : tasksResult) {
            resultList.add(future.get());
        }

        return resultList;
    }

}