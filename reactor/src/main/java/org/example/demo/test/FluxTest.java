package org.example.demo.test;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

public class FluxTest {

    @Test
    public void test1() {
        StepVerifier.create(Flux.range(1, 3))
                .expectNext(1, 2, 3)
                .verifyComplete();
    }

    @Test
    public void test12() {
        Method[] methods = Object.class.getMethods();
        for (Method method : methods) {
            if (method.getReturnType().equals(Void.TYPE)) {
                System.out.println(method.getName());
            }
        }
    }

    /**
     * 响应式流是基于时间的数据流
     * withVirtualTimes使我们不需要使用一天的时间来完成测试
     * <p>
     * 他会在reactor的调度工厂当中插入一个自定义的调度器来代替默认的调度器
     */
    @Test
    public void test2() {
        Duration duration = StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofDays(1)))
                .expectSubscription()
                .expectNoEvent(Duration.ofDays(1))//将订阅也认作一个事件，假设你用它作为第一步，如果检测到有订阅信号，也会失败
                //这时候可以使用 expectSubscription().expectNoEvent(duration)来代替；
                .expectNext(0L)                   //期待一天没有事情发生
                .verifyComplete();//最终会返回一个Duration，这是实际测试的时长
        System.out.println(duration.getSeconds());
    }

    public Mono<String> executeCommand(String command) {
        return Mono.just("");
    }

    //then会忽略所有元素，只保留完成信号，所以返回值为Mono
    public Mono<Void> processOrFallback(Mono<String> commandSource, Mono<Void> doWhenEmpty) {
        return commandSource.flatMap(command -> executeCommand(command)  //1
                .then())
                .switchIfEmpty(doWhenEmpty);                             //2
        //1和2都是 Mono<Void> ，这时候就比较难判断 processOfFallback 中具体执行了哪条路径。这时候可
        //以用 log() 或 doOn*() 等方法来观察，但这“在非绿即红”的单测中不起作用。或者在某个路径加入标识
        //状态的值，并通过判断状态值是否正确来确定，但这就需要修改被测试的 processOfFallback 的代码
        //了
    }

    @Test
    public void testProcessOrFallback() {
        //创建一个探针，它会转化为一个空序列
        PublisherProbe<Void> publisherProbe = PublisherProbe.empty();
        StepVerifier.create(processOrFallback(Mono.empty(), publisherProbe.mono())).verifyComplete();
    }


}
