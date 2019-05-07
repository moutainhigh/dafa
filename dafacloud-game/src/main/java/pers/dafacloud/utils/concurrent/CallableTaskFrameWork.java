package pers.dafacloud.utils.concurrent;

import pers.dafacloud.utils.concurrent.CallableTemplate;
import pers.dafacloud.utils.concurrent.ConcurrentThreadPool;
import pers.dafacloud.utils.concurrent.ICallableTaskFrameWork;
import pers.dafacloud.utils.concurrent.IConcurrentThreadPool;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CallableTaskFrameWork implements ICallableTaskFrameWork {

    private IConcurrentThreadPool concurrentThreadPool = new ConcurrentThreadPool();

    @Override
    public <V> List<V> submitsAll(List<? extends CallableTemplate<V>> tasks)
            throws InterruptedException, ExecutionException {

        concurrentThreadPool.initConcurrentThreadPool();

        return concurrentThreadPool.invokeAll(tasks);
    }

}
