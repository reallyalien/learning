package com.ot.concurrent.pool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * AbortPolicy：直接抛出异常，默认策略；
 * CallerRunsPolicy：用调用者所在的线程来执行任务；
 * DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；获取当前的第一个任务，出队，并执行当前任务
 * DiscardPolicy：直接丢弃任务；空方法
 */
public class MyRejectHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

    }
}
