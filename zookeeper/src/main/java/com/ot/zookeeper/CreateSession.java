package com.ot.zookeeper;

import org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * 创建会话
 */
public class CreateSession {
    //ZooKeeper服务地址
    private static final String SERVER = "127.0.0.1:2181";
    //会话超时时间
    private final int SESSION_TIMEOUT = 30000;

    //发令枪
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 这种获得session的方式可能会在zookeeper还没有获得连接的时候就已经访问了
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(SERVER,SESSION_TIMEOUT,null);
        System.out.println(zooKeeper);
        System.out.println(zooKeeper.getState());
        System.out.println(zooKeeper.getSessionTimeout());
        zooKeeper.create("/zk","kkk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * Unknown;
     * Disconnected;
     * NoSyncConnected;
     * SyncConnected;
     * AuthFailed;
     * ConnectedReadOnly;
     * SaslAuthenticated;
     * Expired;
     * Closed;
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void test2() throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(SERVER, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected){
                    //确认连接完毕之后再进行操作
                    countDownLatch.countDown();
                    System.out.println("已经获得连接");
                }
            }
        });
        countDownLatch.await();
        System.out.println(zooKeeper.getState());
    }

    public static void main(String[] args) {
        System.out.println(BigDecimal.ZERO);
    }
}
