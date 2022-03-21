package com.ot.concurrent.await;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class B {

    public static void main(String[] args) throws InterruptedException {
        int number = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(number);
        long start = System.currentTimeMillis();
        System.out.println("开始");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 200, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000));
        AtomicInteger j = new AtomicInteger(0);
        for (int i = 0; i < number; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(100);
                    j.getAndIncrement();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                }
            });
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        threadPoolExecutor.shutdown();
        System.out.println("执行耗时：" + (end - start) / 1000);

        System.out.println(j);
    }
}
