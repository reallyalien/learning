package com.ot.concurrent.pool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {


    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                200,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(128));
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread());
            }
        };
        for (int i = 0; i < 100; i++) {
            executor.execute(r);
        }
    }
}
