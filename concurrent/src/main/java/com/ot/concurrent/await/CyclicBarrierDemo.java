package com.ot.concurrent.await;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        final CyclicBarrier barrier = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点1，当前已有" +
                                (barrier.getNumberWaiting() + 1) + "个已经到达," + (barrier.getNumberWaiting() == 2 ? "都到齐了，继续走" : "正在等候"));
                        barrier.await();

                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点2，当前已有" +
                                (barrier.getNumberWaiting() + 1) + "个已经到达," + (barrier.getNumberWaiting() == 2 ? "都到齐了，继续走" : "正在等候"));
                        barrier.await();

                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点3，当前已有" +
                                (barrier.getNumberWaiting() + 1) + "个已经到达," + (barrier.getNumberWaiting() == 2 ? "都到齐了，继续走" : "正在等候"));
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            pool.execute(runnable);
        }
        pool.shutdown();
    }
}
