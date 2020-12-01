package com.ot.concurrent.aqs;

import java.util.concurrent.locks.Lock;
import java.util.stream.Stream;

public class SelfTest {
    int count = 0;
    Lock lock = new AqsDemo();

    public void add() {
        try {
            lock.lock();
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int get() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        SelfTest test = new SelfTest();
        Stream.of("t1", "t2", "t3", "t4").forEach(t -> {
            new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    test.add();
                }
            }).start();
        });
        System.out.println("多线程计算完之后：" + test.get());
    }
}
