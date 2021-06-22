package com.ot.concurrent.lockapi;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * CopyOnWrite容器有很多优点，但是同时也存在两个问题，即内存占用问题和数据一致性问题。所以在开发的时候需要注意一下。
 * <p>
 * 　　1.内存占用问题。因为CopyOnWrite的写时复制机制，所以在进行写操作的时候，内存里会同时驻扎两个对象的内存，旧的对象和新写入的对象
 * （注意:在复制的时候只是复制容器里的引用，只是在写的时候会创建新对象添加到新容器里，而旧容器的对象还在使用，所以有两份对象内存）。
 * 如果这些对象占用的内存比较大，比如说200M左右，那么再写入100M数据进去，内存就会占用300M，那么这个时候很有可能造成频繁的Yong GC
 * 和Full GC。之前我们系统中使用了一个服务由于每晚使用CopyOnWrite机制更新大对象，造成了每晚15秒的Full GC，应用响应时间也随之变长。
 * <p>
 * 　　2.针对内存占用问题，可以通过压缩容器中的元素的方法来减少大对象的内存消耗，比如，如果元素全是10进制的数字，可以考虑把它压缩成
 * 36进制或64进制。或者不使用CopyOnWrite容器，而使用其他的并发容器，如ConcurrentHashMap。
 * <p>
 * 　　3.数据一致性问题。CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。所以如果你希望写入的的数据，马上能读到，
 * 请不要使用CopyOnWrite容器。
 * <p>
 * CopyOnWriteArrayList为什么并发安全且性能比Vector好
 * 　我知道Vector是增删改查方法都加了synchronized，保证同步，但是每个方法执行的时候都要去获得锁，性能就会大大下降，
 * 而CopyOnWriteArrayList 只是在增删改上加锁，但是读不加锁，在读方面的性能就好于Vector，CopyOnWriteArrayList支持读多写少的并发情况。
 */
public class CopyOnWriteArrayListDemo {


    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        //频繁变化对象引用，原来的对象失去指引
        Stream.of("t1", "t2", "t3").forEach(t -> new Thread(() -> {
            for (int i = 1; i < 6; i++) {
                list.add(i);
            }
        }, t).start());
        Thread.sleep(100);
        System.out.println(list);
    }
}
