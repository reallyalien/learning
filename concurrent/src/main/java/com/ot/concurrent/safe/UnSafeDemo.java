package com.ot.concurrent.safe;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class UnSafeDemo {

    /**
     * volatile保证可见性，为什么不能保证原子性？
     * 可以保证在每一次读取到的都是新值，但是比如说a线程读到100，此时a线程阻塞，b线程也读到100，+1之后写回到主存，此时a线程+1
     * 然后值被覆盖，因此volatile不能保证原子性。
     */
    private volatile int count;

    private final Object object = new Object();

    public synchronized void add() {
        synchronized (object) {
            count++;
        }
    }

    public int get() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        UnSafeDemo unSafeDemo = new UnSafeDemo();
        Stream.of("t1", "t2", "t3").forEach((t) -> {
            new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    unSafeDemo.add();
                }
            }, t).start();
        });
        TimeUnit.SECONDS.sleep(3);
        System.out.println(unSafeDemo.get());
    }
}
