package com.ot.tools.time;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    private int count;

    @Override
    public void run() {
        System.out.println("hello:" + count++);
    }
}
