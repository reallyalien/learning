package org.example.demo.create;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 慢速的订阅者
 */
public class TestBackPress {
    private static final int EVENT_DURATION = 10;//生成的事件时间间额，单位毫秒
    private static final int EVENT_COUNT = 20;//事件的个数
    private static final int PROCESS_DURATION = 30;//订阅者处理每个元素的时间，单位毫秒

    private Flux<MyEventSource.MyEvent> fastPublisher;
    private SlowSubscriber slowSubscriber;
    private MyEventSource source;
    private static CountDownLatch latch;

    /**
     * 准备工作
     */
    @Before
    public void setup() {
        latch = new CountDownLatch(1);
        slowSubscriber = new SlowSubscriber();
        source = new MyEventSource();
    }

    /**
     * 触发订阅
     */
    @After
    public void subscriber() throws InterruptedException {
        //触发订阅
        fastPublisher.subscribe(slowSubscriber);
        //生成事件
        generateEvent(EVENT_COUNT, EVENT_DURATION);
        latch.await(1, TimeUnit.MINUTES);
    }

    /**
     * 创建一个flux，其中有事件源和已经注册成功的监听器
     *
     * @param strategy
     * @return
     */
    private Flux<MyEventSource.MyEvent> createFlux(FluxSink.OverflowStrategy strategy) {

        return Flux.create((sink) -> {
            source.register(new MyEventListener() {
                @Override
                public void onNewEvent(MyEventSource.MyEvent event) {
                    System.out.println("sink publish: " + event.getMessage());
                    sink.next(event);
                }

                @Override
                public void onEventStopped() {
                    sink.complete();
                }
            });
        }, strategy);
    }

    private void generateEvent(int times, int mills) {
        for (int i = 0; i < times; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(mills);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            source.newEvent(new MyEventSource.MyEvent(new Date(), "event-" + i));
        }
        source.eventStop();
    }

    //===============================================================================================
    static class SlowSubscriber extends BaseSubscriber<MyEventSource.MyEvent> {
        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            //订阅时请求一个数据
            request(1);
        }

        @Override
        protected void hookOnNext(MyEventSource.MyEvent value) {
            System.out.println("receive <<<" + value.getMessage());
            try {
                TimeUnit.MILLISECONDS.sleep(PROCESS_DURATION);
            } catch (InterruptedException e) {

            }
            //每处理完一个数据就再请求一个
            request(1);
        }

        @Override
        protected void hookOnComplete() {
            latch.countDown();
        }

        @Override
        protected void hookOnError(Throwable throwable) {
            System.out.println("receive <<<" + throwable);
        }
    }


    /**
     * 测试回压策略
     */
    @Test
    public void testCreateBackPressureStrategy() {
        fastPublisher = createFlux(FluxSink.OverflowStrategy.ERROR)
                .onBackpressureBuffer()
//                .doOnRequest(n -> System.out.println("     ======request: " + n + " ====="))
                .publishOn(Schedulers.newSingle("newSingle"), 1);
    }


}
