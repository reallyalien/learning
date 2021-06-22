package com.ot.concurrent.await;

import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        Stream.of("t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8").forEach(t -> {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "申请一个" + t);
                    Thread.sleep(2000);
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + "释放一个" + t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, t).start();
        });
    }
}
