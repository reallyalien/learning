package com.ot.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ZkLock {

    private static final String CONNECTSTRING = "192.168.140.128:2181,192.168.140.128:2182,192.168.140.128:2183";
    private static final String LOCKPATH = "/pay/lock";
    private static CuratorFramework client =
            CuratorFrameworkFactory
                    .builder()
                    .connectString(CONNECTSTRING)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();

    private static Integer n = 0;

    public static void add() {
        n++;
    }


    public static void main(String[] args) throws InterruptedException {
        client.start();
        final InterProcessMutex lock=new InterProcessMutex(client,LOCKPATH);
        Stream.of("t1","t2","t3","t4").forEach(t -> {
            new Thread(() -> {
                try {
                    lock.acquire();
                    for (int i = 0; i < 10000; i++) {
                        add();
                    }
                }catch (Exception e){

                }finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
        Thread.sleep(15000);
        System.out.println(n);
    }
}
