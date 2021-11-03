package com.ot.concurrent.pool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPoolExecutorTest {

    /**
     * 周期执行的任务 必须要捕获异常，否则任务会停止执行
     *
     * @param args
     */
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor ex = new ScheduledThreadPoolExecutor(8);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
//                    int a = 1 / 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Date date = new Date();
                String s = format.format(date);
                System.out.println(Thread.currentThread() + "......" + s);
            }
        };
        //此方法是已上一个任务开始的时间计时，period时间过去之后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，则当前任务立即执行，
        //如果上一个任务没有执行完毕，则需要等上一个任务执行完毕之后再执行
//        ex.scheduleAtFixedRate(r, 5, 3, TimeUnit.SECONDS);
        //以上一个任务结束开始计时，period时间过去之后，立即执行
        ex.scheduleWithFixedDelay(r, 5, 3, TimeUnit.SECONDS);
    }
}
