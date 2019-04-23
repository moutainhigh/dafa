package pers.dafacloud.concurrent;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface IConcurrentThreadPool {
    /**
     * 初始化线程池
     */
    void initConcurrentThreadPool();

    /**
     * 提交单个任务
     *
     * @param task
     * @return
     */
    <V> V submit(CallableTemplate<V> task) throws InterruptedException,
            ExecutionException;

    /**
     * 提交多个任务
     *
     * @param tasks
     * @return
     */
    <V> List<V> invokeAll(List<? extends CallableTemplate<V>> tasks)
            throws InterruptedException, ExecutionException;
}

