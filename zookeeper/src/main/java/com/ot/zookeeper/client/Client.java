package com.ot.zookeeper.client;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Client {
    private static CountDownLatch latch = new CountDownLatch(1);

    private static ZooKeeper client = null;

    private static String addr = "192.168.140.128:2181,192.168.140.128:2182,192.168.140.128:2183";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        client = new ZooKeeper(addr, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    latch.countDown();
                }
                try {
                    List<String> children = client.getChildren("/servers", true);
                    System.out.println("================================");
                    System.out.println(children);
                    System.out.println("================================");
                } catch (Exception e) {

                }
            }
        });
        latch.await();
        while (true) {

        }
    }

}
