package com.ot.zookeeper.study;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class Test1 {
    private static String url = "192.168.140.128:2181,192.168.140.128:2182,192.168.140.128:2183";

    private static int timeout = 20000;

    ZooKeeper zooKeeper = null;

    @Before
    public void init() {
        try {
            zooKeeper = new ZooKeeper(url, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println(event.getState());
                }
            });
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create() throws KeeperException, InterruptedException {
        String node = zooKeeper.create("/baidu", "baidu".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(node);
    }
}
