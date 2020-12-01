package com.ot.concurrent.basic;

import java.sql.Connection;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
在高并发下确保任务只执行一次
在很多高并发的环境下，往往我们只需要某些任务只执行一次。这种使用情景FutureTask的特性恰能胜
任。举一个例子，假设有一个带key的连接池，当key存在时，即直接返回key对应的对象；当key不存在
时，则创建连接。对于这样的应用场景，通常采用的方法为使用一个Map对象来存储key和连接池对应
的对应关系，典型的代码如下面所示：
在上面的例子中，我们通过加锁确保高并发环境下的线程安全，也确保了connection只创建一次，然而
确牺牲了性能。改用ConcurrentHash的情况下，几乎可以避免加锁的操作，性能大大提高，但是在高
并发的情况下有可能出现Connection被创建多次的现象。这时最需要解决的问题就是当key不存在时，
创建Connection的动作能放在connectionPool之后执行，这正是FutureTask发挥作用的时机，基于
ConcurrentHashMap和FutureTask的改造代码如下：
 */
public class FutureTaskDemo {

    private final ConcurrentHashMap<String, FutureTask<Connection>> pool = new ConcurrentHashMap<>();

    public Connection getConnection(String key) throws ExecutionException, InterruptedException {
        FutureTask<Connection> futureTask = pool.get(key);
        if (futureTask != null) {
            return futureTask.get();
        } else {
            Callable<Connection> callable = new Callable<Connection>() {
                @Override
                public Connection call() throws Exception {
                    return createConnection();
                }
            };
            FutureTask<Connection> newFutureTask = new FutureTask<Connection>(callable);
            futureTask=pool.putIfAbsent(key, newFutureTask);
        }
        return futureTask.get();
    }

    public Connection createConnection() {
        return null;
    }
}
