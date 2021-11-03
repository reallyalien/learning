package com.ot.zookeeper.config;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MyZookeeper {

    private static CountDownLatch latch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();


    public static void main(String[] args) {
        String path = "/";
        String addr = "192.168.140.128:2181,192.168.140.128:2182,192.168.140.128:2183";
        try {
            zooKeeper = new ZooKeeper(addr, 3000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("watch线程：" + Thread.currentThread().getName());
                    System.out.println("连接状态：" + watchedEvent.getState());
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                    List<String> children = null;
                    try {
                        children = zooKeeper.getChildren("/", true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(children);
                }
            });
            latch.await();
            //ephemeral_sequential
            //创建节点
//            String node = zooKeeper.create("/baidu", "baidu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
//            System.out.println(node);
            //获取节点
//            List<String> children = zooKeeper.getChildren("/", true);
//            System.out.println(children);
            //判断节点是否存在
            Stat exists = zooKeeper.exists("/sanguo", false);
            System.out.println(exists);
            while (true) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}