package com.ot.concurrent.lockapi;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    private static ThreadLocal<Integer> context = new ThreadLocal<>();

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                8,
                8,
                3000,
                TimeUnit.SECONDS,
                null);
        SecurityManager securityManager = System.getSecurityManager();
        System.out.println(securityManager.getThreadGroup());
    }
}
