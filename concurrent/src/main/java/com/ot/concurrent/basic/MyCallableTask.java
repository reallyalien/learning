package com.ot.concurrent.basic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class MyCallableTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(5);
        return "call";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyCallableTask());
        new Thread(futureTask).start();
        boolean cancel = futureTask.cancel(true);//参数表示是否允许取消正在执行而没有执行完的任务
        System.out.println(cancel);
        String result = futureTask.get();//此方法会组阻塞
        System.out.println("main线程执行完毕");
        System.out.println(result);
    }
}
