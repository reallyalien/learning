package org.example.demo;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class FluxTest {
    /**
     * 通过列举方式创建
     */
    @Test
    public void test1() {
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        List<String> list = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(list);
    }

    /**
     * 工厂方法的其他例子
     * 注意，
     * 即使没有值，工厂方法仍然采用通用的返回类型。
     * 第一个参数是 range 的开始，第二个参数是要生成的元素个数。
     */
    @Test
    public void test2() {
        Mono<Object> empty = Mono.empty();
        Mono<String> foo = Mono.just("foo");
        Flux<Integer> range = Flux.range(5, 3);
    }

    /**
     * subscribe方法示例
     */
    @Test
    public void test3() {
        //配置一个在订阅是会产生3个值的flux
        Flux<Integer> range = Flux.range(-1, 1);
        //最简单的订阅方式
        range.subscribe();
        //传入consumer，订阅它并打印值
        range.subscribe(System.out::println);
    }

    /**
     * 异常，错误处理
     */
    @Test
    public void test4() {
        //配置一个在订阅时产生4个值的flux，为了对元素处理，我们需要一个map操作，对于多数元素，我们返回其本身，对其中一个元素抛出异常
        Flux<Integer> flux = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) {
                        System.out.println("map线程：" + Thread.currentThread());
                        return i;
                    }
                    throw new RuntimeException("4");
                });
        //定义的时候对抛出异常的元素该怎么处理
        flux.subscribe(System.out::println, error -> System.out.println("error:" + error));
    }

    /**
     * 正常，异常，完成
     */
    @Test
    public void test5() {
        //配置一个在订阅时产生4个值的flux
        Flux<Integer> range = Flux.range(1, 4).map(i -> {
            if (i <= 3) {
                return i;
            }
            throw new RuntimeException("eee");
        });
        //正确处理，异常处理，完成处理的相应consumer和runnable
        //异常和完成只能出现一个，错误和完成都是终止信号，只能出现一次
        range.subscribe(
                System.out::println,
                error -> System.out.println("error:" + error),
                () -> System.out.println("done")
        );
    }

    /**
     * 回压，背压，流量控制
     */
    @Test
    public void test6() {
        SampleSubscriber<Integer> sampleSubscriber = new SampleSubscriber<>();
        //假设我们现在有一个非常快的publisher,然后自定义一个每秒只能处理一个元素的subscriber,这是subscriber就需要通过request(n)的方式告知
        //上游它的需求速度
        //1. Flux.range`是一个快的Publisher；
        //2. 在每次`request`的时候打印request个数；
        //3. 通过重写`BaseSubscriber`的方法来自定义Subscriber；
        //4. `hookOnSubscribe`定义在订阅的时候执行的操作；
        //5. 订阅时首先向上游请求1个元素；
        //6. `hookOnNext`定义每次在收到一个元素的时候的操作；
        //7. sleep 1秒钟来模拟慢的Subscriber；
        //8. 打印收到的元素；
        //9. 每次处理完1个元素后再请求1个。
        //range方法生成的flux是以缓存的方式的回压策略，能够缓存暂时还无法处理的元素
        Flux.range(1, 6)
                .doOnRequest(n -> System.out.println("request " + n + " values"))
                .subscribe(new BaseSubscriber<Integer>() {
                    //在订阅时候的操作
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("Subscribed and make a request...");
                        request(1);
                    }

                    //在每次收到一个元素时的操作
                    @Override
                    protected void hookOnNext(Integer value) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Get value [" + value + "]");    // 8
                        request(1);
                    }
                });
    }

    /**
     * 最简单的创建 Flux 的方式就是使用 generate 方法。
     * <p>
     * 这是一种同步地， 逐个地产生值的方法，意味着 sink 是一个 SynchronousSink 而且其 next() 方法在每次
     * 回调的时候最多只能被调用一次。你也可以调用 error(Throwable) 或者 complete()，不过是可选的。
     * <p>
     * 最有用的一种方式就是同时能够记录一个状态值（state），从而在使用 sink 发出下一个元素的时候能够基于这
     * 个状态值去产生元素。此时生成器（generator）方法就是一个 BiFunction<S, SynchronousSink<T>, S>， 其中
     * <S> 是状态对象的类型。你需要提供一个 Supplier<S> 来初始化状态值，而生成器需要 在每一“回合”生成元
     * 素后返回新的状态值（供下一回合使用）。
     * <p>
     * 例如我们使用一个 int 作为状态值。
     * 基于状态值的 generate 示例
     */
    @Test
    public void test7() {

        //sink
        //英 [sɪŋk]   美 [sɪŋk]
        //v.
        //下沉;下陷;沉没;使下沉;使沉没;倒下;坐下
        //n.
        //(厨房里的)洗涤池，洗碗槽
        //adj.
        //位于社会条件差的贫穷地区的;贫民窟的


        //1.初始状态值为0
        //2.我们基于状态值state来生成下一个状态值state*3
        //3.我们也可以使用状态值来终止序列
        //3.返回一个新的状态值，来供下次调用
        Flux<Object> flux = Flux.generate(
                () -> 0,
                (state, synchronousSink) -> {
                    synchronousSink.next("3 x state" + " = " + 3 * state);
                    if (state == 10) {
                        synchronousSink.complete();
                    }
                    return state + 1;
                });
        flux.subscribe(System.out::println);
    }

    /**
     * 使用可变类型，上述的Integer属于不可变类型
     */
    @Test
    public void test8() {
        //1.使用AtomicInteger的构造方法初始化state
        //2.每次增加1
        //3.返回同一个实例作为新的状态值，其实已经增加1
        Flux<Object> flux = Flux.generate(
                AtomicInteger::new,
                (state, synchronousSink) -> {
                    int i = state.getAndIncrement();
                    synchronousSink.next("3 x i" + " = " + 3 * i);
                    if (i == 10) {
                        synchronousSink.complete();
                    }
                    return state;
                });
        flux.subscribe(System.out::println);
    }

    /**
     * 增加一个consumer，用来清理状态对象consumer在序列终止时才被调用
     * 如果state使用了数据库链接或者其他需要最终清理的资源，那么这个consumer可以用来在最后关闭连接或完成最后的清理工作
     */
    @Test
    public void test9() {
        //1.使用AtomicInteger的构造方法初始化state
        //2.每次增加1
        //3.返回同一个实例作为新的状态值，其实已经增加1
        Flux<Object> flux = Flux.generate(
                AtomicInteger::new,
                (state, synchronousSink) -> {
                    int i = state.getAndIncrement();
                    synchronousSink.next("3 x i" + " = " + 3 * i);
                    if (i == 10) {
                        synchronousSink.complete();
                    }
                    return state;
                },
                state -> System.out.println("state:" + state)
        );
        flux.subscribe(System.out::println);
    }

    /**
     * 作为一个更高级的创建 Flux 的方式， create 方法的生成方式既可以是同步， 也可以是异步的，并且还可以每次发出多个元素。
     * 该方法用到了 FluxSink，后者同样提供 next，error 和 complete 等方法。 与 generate 不同的是，create 不需要状态值，
     * 另一方面，它可以在回调中触发 多个事件（即使是在未来的某个时间）。
     * <p>
     * create 有个好处就是可以将现有的 API 转为响应式，比如监听器的异步方法。
     */
    @Test
    public void test10() {
        //1.假如你有一个监听器api，它按chunk处理事件，有2种事件
        //2.一个chunk处理事件到达之后的事情，另一个处理事件处理完成之后的事情
        //可以使用create的api将其转变成响应式api
    }

    @Test
    public void test11() {
        /*
        首先，错误信号和完成信号都是终止信号，二者不可能同时共存；
        如果没有发出任何一个元素值，而是直接发出完成/错误信号，表示这是一个空数据流；
        如果没有错误信号和完成信号，那么就是一个无限数据流。
         */
        // 只有完成信号的空数据流
        Flux.just(); //just方法仅仅是声明了这个数据流，此时数据元素尚未发出，只有subscribe之后才会触发数据流，所以订阅之前
        //什么都不会发生
        Flux.empty();
        Mono.empty();
        Mono.justOrEmpty(Optional.empty());
        // 只有错误信号的数据流
        Flux.error(new Exception("some error"));
        Mono.error(new Exception("some error"));
        //比如从DB中获取数据，都需要通过完成信号或者终止信号告知订阅者，已经查询完毕，但是没有获取到值而已
    }

    //===============================================================================================================

    private Flux<Integer> generateFluxFrom1To6() {
        return Flux.just(1, 2, 3, 4, 5, 6);
    }

    private Mono<Integer> generateMonoWithError() {
        return Mono.error(new Exception("some error"));
    }

    @Test
    public void testViaStepVerifier() {
        StepVerifier.create(generateFluxFrom1To6())
                .expectNext(1, 2, 3, 4, 5, 6)//用于测试下一个期望的元素
                .expectComplete()//用于校验下一个元素是否是完成信号
                .verify();
        StepVerifier.create(generateMonoWithError())
                .expectErrorMessage("some error")//用于校验下一个元素是否为错误信息信号
                .verify();
    }

    /**
     * 通常情况下，我们需要对源发布者发出的原始数据流进行多个阶段的处理，并最终得到我们需要的数据。这种感觉就像是一条流水线，
     * 从流水线的源头进入传送带的是原料，经过流水线上各个工位的处理，逐渐由原料变成半成品、零件、组件、成品，最终成为消费者
     * 需要的包装品。这其中，流水线源头的下料机就相当于源发布者，消费者就相当于订阅者，流水线上的一道道工序就相当于一个一个
     * 的操作符（Operator）。
     * <p>
     * Map
     */
    @Test
    public void testOperator() {

        //map
        StepVerifier.create(
                Flux.range(1, 3)
                        .map(i -> i * i))
                .expectNext(1, 4, 9)
                .verifyComplete();
        //flatMap 将每个数据元素转换成流，然后将这些流合并成一个大的数据流,流的合并是异步的，先来先到，并非按顺序执行
        //flatMap也是接收一个Function的函数式接口为参数，这个函数式的输入为一个T类型数据值，对于Flux来说输出可以是
        //Flux和Mono，对于Mono来说输出只能是Mono。举例说明：
        //1.对于每一个字符串s,将其拆分为包含一个字符的字符串流
        //2.对于每一个字符延迟100ms
        //3.doOnNext是偷窥式的方法，不会消费数据流
        //4.验证是否发送了8个元素
        StepVerifier.create(
                Flux.just("flux", "mono")
                        .flatMap(s -> Flux.fromArray(s.split("\\s*")).delayElements(Duration.ofMillis(100)))
                        .doOnNext(System.out::print))
                .expectNextCount(8)
                .verifyComplete();

        //filter
        StepVerifier.create(
                Flux.range(1, 6)
                        .filter(i -> i % 2 == 1)
                        .map(i -> i * i))
                .expectNext(1, 9, 25)
                .verifyComplete();


    }

    /**
     * zip合并,将两个流合并成一个流，每次从flux或者mono当中各取一个元素，合并成一个二元组
     */
    @Test
    public void testZip() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        //使用flux.interval声明一个每200ms发出一个元素的Long数据流，因为zip操作是一对一的，故而将其他字符串zip之后，字符串流也会具体相同的速度
        //zip之后流中的元素变成Tuple2,使用getT1方法获取字符串流当中的元素；定义完成之后latch.countDown
        //latch.await会等待10s钟
        //除了静态方法之外，还有zipWith等非静态方法，效果是一样的
        //在异步的条件下，数据流的流速不同,使用zip可以一对一的将两个数据流对齐发出
        Flux.zip(getZipDescFlux(), Flux.interval(Duration.ofMillis(200)))
                .subscribe(
                        t -> System.out.println(t.getT1()),
                        null,
                        latch::countDown);
        latch.await(10, TimeUnit.SECONDS);
    }

    private Flux<String> getZipDescFlux() {
        String desc = "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.";
        return Flux.fromArray(desc.split("\\s+"));
    }

    /**
     * reactor提供的线程池
     */
    public void testSchedule() {
        Scheduler immediate = Schedulers.immediate();
        Scheduler single = Schedulers.single();
        //Executors提供的几种线程池在Reactor中都支持：
        //Schedulers.single()和Schedulers.newSingle()对应Executors.newSingleThreadExecutor()；
        //Schedulers.elastic()和Schedulers.newElastic()对应Executors.newCachedThreadPool()；
        //Schedulers.parallel()和Schedulers.newParallel()对应Executors.newFixedThreadPool()；
        //下一章会介绍到，Schedulers提供的以上三种调度器底层都是基于ScheduledExecutorService的，因此都是支持任务定时和周期性执行的

        //Flux和Mono的调度操作符subscribeOn和publishOn支持work-stealing。 工作窃取算法
    }

    //定义一个同步阻塞的方法
    //正常情况下，这个方法会被阻塞2s钟之后才能返回
    public String getStringSync() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello reactor";
    }

    /**
     * 切换调度器的操作符
     * <p>
     * Reactor 提供了两种在响应式链中调整调度器 Scheduler的方法：publishOn和subscribeOn。它们都接受一个 Scheduler
     * 作为参数，从而可以改变调度器。但是publishOn在链中出现的位置是有讲究的，而subscribeOn 则无所谓。
     * <p>
     * 比如有如下代码：
     * Flux.range(1, 1000)
     * .map(…)
     * .publishOn(Schedulers.elastic()).filter(…)
     * .publishOn(Schedulers.parallel()).flatMap(…)
     * .subscribeOn(Schedulers.single())
     * publishOn会影响链中其后的操作符，比如第一个publishOn调整调度器为elastic，则filter的处理操作是在弹性线程池中执行的；
     * 同理，flatMap是执行在固定大小的parallel线程池中的；
     * subscribeOn无论出现在什么位置，都只影响源头的执行环境，也就是range方法是执行在单线程中的，直至被第一个publishOn切
     * 换调度器之前，所以range后的map也在单线程中执行。
     *
     * @throws InterruptedException
     */
    @Test
    public void testSync() throws InterruptedException {

        //使用fromCallable声明一个基于Callable的Mono；
        //使用subscribeOn将任务调度到Schedulers内置的弹性线程池执行，弹性线程池会为Callable的执行任务分配一个单独的线程
        CountDownLatch latch = new CountDownLatch(1);
        Mono.fromCallable(() -> getStringSync())
                .subscribeOn(Schedulers.elastic())
                .subscribe(
                        s -> {
                            System.out.println(s);
                            System.out.println("当前线程：" + Thread.currentThread());
                        },
                        error -> System.out.println("error:" + error),
                        () -> {
                            latch.countDown();
                            System.out.println("当前线程：" + Thread.currentThread());
                        }
                );
        //主线程可以继续执行
        System.out.println("主线程：" + Thread.currentThread());
        latch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void test12() throws InterruptedException {
        //当i为3的时候，抛出异常，返回0，终止流
        Flux.range(1, 6)
                .map(i -> 10 / (i - 3))
                .onErrorReturn(0)
                .map(i -> i * i)
                .subscribe(
                        System.out::println,
                        System.err::println
                );
        //捕获并执行一个异常处理的方法或计算一个候补值
        Flux.range(1, 6)
                .map(i -> 10 / (i - 3))
                .doOnError(e -> {
                    //记录错误日志，只读操作，不会对流造成任何影响，错误继续向下传递
                    System.out.println(e.toString());
                })
                .onErrorResume(e -> Mono.just(new Random().nextInt(9)))
                .map(i -> i * i)
                .subscribe(
                        System.out::println,
                        System.err::println
                );
        System.out.println("=============================================================================");
        //doFinally 在序列终止的时候进行，还能判断是什么类型的终止事件，以便可以针对性的清理
        //1.用longAddr进行统计，如果是取消，那么统计数量自增，take(1)能够在发出一个元素之后取消流
        LongAdder statsCancel = new LongAdder();
        Flux.just("foo", "aaa", "aaaa")
                .doFinally(type -> {
                    if (type == SignalType.CANCEL) {
                        statsCancel.increment();
                        System.out.println(statsCancel);
                    }
                })
                .take(3)
                .subscribe(System.out::println);

        System.out.println("================================================================================");
        //重试，对上游的flux是采取重新订阅的方式，已经是新的数据流了,
        Flux.range(1, 6)
                .map(i -> 10 / (i - 3))
                .retry(1)
                .subscribe(System.out::println, System.err::println);
        Thread.sleep(1000);

    }

    @Test
    public void test13() throws InterruptedException {
        Flux.interval(Duration.ofMillis(1)).subscribe(System.out::println);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void test14() throws InterruptedException {
        Flux.range(1, 10)
                .map(i -> {
                    if (i ==1){
                        System.out.println("map线程：" + Thread.currentThread());
                    }
                    return i;
                })
                .publishOn(Schedulers.elastic())
                .filter(i -> {
                    if (i==1){
                        System.out.println("filter线程：" + Thread.currentThread());
                    }
                    return i % 2 == 0;
                })
                .publishOn(Schedulers.parallel())
                .flatMap(i -> {
                    if (i==2){
                        System.out.println("flatMap线程：" + Thread.currentThread());
                    }
                    return Mono.just("");
                })
//                .subscribeOn(Schedulers.single())
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(3);
    }

}
