package com.ethan.janus.core.threadpool;

import java.util.concurrent.ExecutorService;

/**
 * 执行比对分支的线程池 信息Provider
 */
public interface JanusBranchThreadPoolMetricsProvider {

    /**
     * 获取线程池队列信息当前的 size
     *
     * @param executor 线程池对象
     * @return 线程池队列信息当前的 size
     */
    int getQueueSize(ExecutorService executor);

    /**
     * 获取线程池队列最大容量
     *
     * @param executor 线程池对象
     * @return 线程池队列最大容量
     */
    int getQueueCapacity(ExecutorService executor);
}
