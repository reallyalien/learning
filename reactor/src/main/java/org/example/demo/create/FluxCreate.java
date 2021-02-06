package org.example.demo.create;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FluxCreate {
    /*
        create 方法还有一个变体方法 push ，适合生成事件流。与 create 类似， push 也可以是异步地，并且
        能够使用以上各种回压策略。所以上边的例子可以替换为 push 方法。区别在于， push 方法中，调用
        next 、 complete 或 error 的必须是同一个线程。
     */

    /*
        1. 事件源；
        2. 向事件源注册用匿名内部类创建的监听器；
        3. 监听器在收到事件回调的时候通过sink将事件再发出；
        4. 监听器再收到事件源停止的回调的时候通过sink发出完成信号；
        5. 触发订阅（这时候还没有任何事件产生）；
        6. 循环产生20个事件，每个间隔不超过1秒的随机时间；
        7. 最后停止事件源。
     */
    @Test
    public void test1() throws InterruptedException {
        //初始化一个事件源
        MyEventSource source = new MyEventSource();
        Flux.create(sink -> {
            //向事件源添加一个监听器
            source.register(new MyEventListener() {
                @Override
                public void onNewEvent(MyEventSource.MyEvent event) {
                    //这个监听器收到事件回调的时候通过sink将一系列的事件转换成异步的事件流
                    sink.next(event);
                }

                @Override
                public void onEventStopped() {
                    sink.complete();
                }
            });
        }).subscribe(System.out::println);
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            //提交事件
            source.newEvent(new MyEventSource.MyEvent(new Date(), "event-" + i));
        }
        source.eventStop();

    }
}
