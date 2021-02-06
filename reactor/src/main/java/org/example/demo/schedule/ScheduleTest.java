package org.example.demo.schedule;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 线程调度模型
 */
public class ScheduleTest {

    private Supplier supplier = ArrayList::new;

    @Test
    public void test1() throws InterruptedException {
        Flux.range(1, 3)
                .map(i -> {
                    if (i == 1) {
                        System.out.println("map线程：" + Thread.currentThread());
                    }
                    return i;
                })
                .publishOn(Schedulers.elastic())
                .filter(i -> {
                    System.out.println("filter线程：" + Thread.currentThread());
                    return i % 2 == 0;
                })
                .publishOn(Schedulers.parallel())
                .flatMap(i -> {
                    if (i == 2) {
                        System.out.println("flatMap线程：" + Thread.currentThread());
                    }
                    return Mono.just("");
                })
                .subscribeOn(Schedulers.single())
                .blockLast();
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void test2() {
        Flux.range(1, 10).delayElements(Duration.ofSeconds(1)).log().blockLast();
    }

    /**
     * ParallelFlux 会将每个元素调度到多个执行轨道上
     */
    @Test
    public void test3() throws InterruptedException {
        Flux.range(1, 10)
                .parallel(4)
                .runOn(Schedulers.parallel())
                .log()
                .subscribe();
        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * 打包操作
     */
    @Test
    public void transform() {
        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> f.filter(color -> !color.equals("orange")).map(String::toUpperCase);
        Flux.fromIterable(Arrays.asList("blue", "green", "orange", "pink"))
                .doOnNext(System.out::println)
                .transform(filterAndMap)
                .subscribe(s -> System.out.println("Subscriber to Transformed MapAndFilter:" + s));
    }

    /**
     * compose 操作符与 transform 类似，也能够将几个操作符封装到一个函数式中。主要的区别就是，这
     * 个函数式是针对每一个订阅者起作用的。这意味着它对每一个 subscription 可以生成不同的操作链。举
     * 个例子：
     */
    @Test
    public void compose() {
        AtomicInteger ai = new AtomicInteger();
        Function<Flux<String>, Flux<String>> filterMap = f -> {
            if (ai.incrementAndGet() == 1) {
                return f.filter(color -> !color.equals("orange")).map(String::toUpperCase);
            }
            return f.filter(color -> !color.equals("pink")).map(String::toUpperCase);
        };

        Flux<String> composeFlux = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "pink"))
                .doOnNext(System.out::println)
                .compose(filterMap);
        composeFlux.subscribe(d -> System.out.println("Subscriber 1 to Composed MapAndFilter :" + d));
        composeFlux.subscribe(d -> System.out.println("Subscriber 2 to Composed MapAndFilter: " + d));
    }
}
