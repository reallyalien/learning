package com.ot.concurrent.pool;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentHashMapTest {
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
//        HashMap<String, Integer> map = new HashMap<>();
        map.put("key", 0);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 50; i++) {
            executorService.submit(() -> {
                //每个线程获取的值假如都是最新的，当第一个线程+1准备写入时，第二个线程获取到的跟第一个线程是一样的，+1之后，在put
                //操作时，这时候会加锁，即使这样，第一个线程写入1，第二个线程稍后写入的也是1，值被覆盖,因为这两行代码已经不是原子
                //操作了,concurrentHashMap只能保证它自己内部线程安全
                synchronized (lock) {
                    int key = map.get("key");
                    map.put("key", key + 1);
//                    map.computeIfAbsent()
                }
            });
        }
        Thread.sleep(3000);
        executorService.shutdown();
        System.out.println(map.get("key"));
    }
}
