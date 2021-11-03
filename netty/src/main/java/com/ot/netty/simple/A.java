package com.ot.netty.simple;

import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

public class A {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(10);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread());
            }
        };
        for (int i = 0; i < 20; i++) {
            group.execute(runnable);
        }
    }


}
