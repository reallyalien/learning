package com.ot.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 分布式锁实现
 */
public class DistributedLock {
    private CountDownLatch latch = new CountDownLatch(1);
    private CountDownLatch wait = new CountDownLatch(1);
    private ZooKeeper zooKeeper = null;
    private String addr = "192.168.140.128:2181,192.168.140.128:2182,192.168.140.128:2183";
    private String currentNode;
    private String waitpath = null;

    public DistributedLock() throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(addr, 20000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    latch.countDown();
                }
                if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitpath)) {
                    wait.countDown();
                }
            }
        });
        latch.await();
        Stat stat = zooKeeper.exists("/locks", false);
        if (stat == null) {
            zooKeeper.create("/locks", "locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public void lock() throws KeeperException, InterruptedException {
        String currentNode = zooKeeper.create("/locks/seq", "seq-".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        this.currentNode = currentNode;
        List<String> children = zooKeeper.getChildren("/locks", false);
        if (children == null || children.size() == 1) {
            return;
        }
        Collections.sort(children);
        int index = children.indexOf(currentNode.substring("/locks/".length()));
        if (index == 0) {
            return;
        } else {
            //监听前一个节点的变化
            String watchPath = "/locks/" + children.get(index - 1);
            this.waitpath = watchPath;
            zooKeeper.getData(watchPath, true, new Stat());
            wait.await();
        }
    }

    public void unlock() {
        try {
            zooKeeper.delete(this.currentNode, -1);
        } catch (Exception e) {
        }
    }
}
