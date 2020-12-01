package com.ot.nio.transform;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerPool extends Server {
    private ExecutorService exec = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

    }

    public void readData(SelectionKey key) throws IOException {
        // 移除掉这个key的可读事件，已经在线程池里面处理,如果不改变当前Key的状态，
        // 这里交给另外一个线程去处理，主线程下一次遍历此KEY还是可读事件，会重复开启线程处理任务
        key.interestOps(key.interestOps() & (-SelectionKey.OP_READ));
        exec.execute(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            FileChannel fileChannel = fileMap.get(key);
            buffer.clear();
            SocketChannel socketChannel = (SocketChannel) key.channel();
            int len = 0;
            try {
                while ((len = socketChannel.read(buffer)) > 0) {
                    buffer.flip();
                    fileChannel.write(buffer);
                    buffer.clear();
                }
            } catch (IOException e) {
                key.cancel();
                e.printStackTrace();
                return;
            }
            if (len == -1) {
                try {
                    fileChannel.close();
                    System.out.println("上传完毕");
                    buffer.put((socketChannel.getRemoteAddress() + "上传成功").getBytes());
                    buffer.clear();
                    socketChannel.write(buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //只有调用cancel才会真正的从选择的键的集合里删除，否则下次select的时候又能看到
                key.cancel();
                return;
            }
            // Channel的read方法可能返回0，返回0并不一定代表读取完了。
            // 工作线程结束对通道的读取，需要再次更新键的ready集合，将感兴趣的集合重新放 在里面
            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
            // 调用wakeup，使得选择器上的第一个还没有返回的选择操作立即返回即重新 select
            //在使用Selector对象的 select() 或者 select(long) 方法时候，当前线程很可能一直阻塞下去，那么用另一个线程去执行 Selector.wakeUp() 方法会唤醒当前被阻塞的线程，使其 select() 立即返回。
            //当然，如果当前线程没有阻塞，那么执行了wakeUp() 方法之后，下一个线程的 select() 方法会被立即返回，不再被阻塞下去。
            key.selector().wakeup();
        });
    }
}
