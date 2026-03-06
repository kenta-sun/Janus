package com.ethan.janus.core.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class JanusBranchThreadPoolDefaultProvider implements JanusBranchThreadPoolMetricsProvider {

    @Override
    public int getQueueSize(ExecutorService executor) {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
        return threadPoolExecutor.getQueue().size();
    }

    @Override
    public int getQueueCapacity(ExecutorService executor) {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
        // 线程池队列
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        // 队列当前 size
        int currentSize = queue.size();
        // 队列剩余 size
        int remainingCapacity = queue.remainingCapacity();
        // 队列总 size
        return currentSize + remainingCapacity;
    }
}
