package com.ot.concurrent.lockapi.readandwrite;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockQueue {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);
        queue.put(1);
        queue.put(2);
        queue.put(3);
        Integer take = queue.take();
        System.out.println(take);
    }
}
