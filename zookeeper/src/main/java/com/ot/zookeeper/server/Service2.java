package com.ot.zookeeper.server;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Service2 {
    private static CountDownLatch latch = new CountDownLatch(1);

    private static String addr = "192.168.140.128:2181,192.168.140.128:2182,192.168.140.128:2183";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper client = new ZooKeeper(addr, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    latch.countDown();
                }
            }
        });
        latch.await();
        String node = client.create("/servers/node2", "node2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(node);
        while (true) {

        }
    }
}
