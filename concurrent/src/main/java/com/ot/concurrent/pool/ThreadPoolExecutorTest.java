package com.ot.concurrent.pool;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.concurrent.*;

public class ThreadPoolExecutorTest {


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                8,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10), new RejectHandler());
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "执行了");
            }
        };
        for (int i = 0; i < 100; i++) {
            executor.submit(r);
        }
    }

    static class RejectHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("当前任务：" + r + "被拒绝");
        }
    }
}
