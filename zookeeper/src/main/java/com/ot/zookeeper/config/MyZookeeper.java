package com.ot.zookeeper.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MyZookeeper {

    private static CountDownLatch latch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();


    public static void main(String[] args) {
        String path = "/username";
        String addr = "127.0.0.1:2181";
        try {
            zooKeeper = new ZooKeeper(addr, 3000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("watch线程："+Thread.currentThread().getName());
                    System.out.println("连接状态：" + watchedEvent.getState());
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            System.out.println("main线程："+Thread.currentThread().getName());
            latch.await();
            List<String> children = zooKeeper.getChildren("/", null);
            byte[] data = zooKeeper.getData(path, null, null);
            System.out.println(new String(data));
            while (true) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}