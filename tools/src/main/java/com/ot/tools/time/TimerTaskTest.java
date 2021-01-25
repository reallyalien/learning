package com.ot.tools.time;

import java.util.Timer;

public class TimerTaskTest {

    public static void main(String[] args) {
        MyTimerTask task = new MyTimerTask();
        Timer timer = new Timer();
        long delay = 0;
        long intervalPeriod = 1 * 1000;
        timer.schedule(task, delay, intervalPeriod);
    }
}
