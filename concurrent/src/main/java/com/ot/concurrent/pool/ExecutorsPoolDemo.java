package com.ot.concurrent.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ExecutorsPoolDemo {

    public static void main(String[] args) throws Exception {
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
//                10,
//                1000,
//                TimeUnit.SECONDS,
//                new LinkedBlockingQueue(),
//                null,
//                null);
        ExecutorsPoolDemo demo = new ExecutorsPoolDemo();
        demo.errorWork();
    }
    public void errorWork() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Runnable runnable=()->{
            throw new NullPointerException();
        };
        Future<?> future = executorService.submit(runnable);
        System.out.println(future.get());
        Thread.sleep(5000);
        System.out.println("finished");

    }
}
