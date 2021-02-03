package org.example.demo;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

/**
 * 自定义简单的subscriber
 * SampleSubscriber 类继承自 BaseSubscriber，在 Reactor 中, 推荐用户扩展它来实现自定义的 Subscriber。
 * 这个类提供了一些 hook 方法，我们可以通过重写它们来调整 subscriber 的行为。 默认情况下，它会触发一
 * 个无限个数的请求，但是当你想自定义请求元素的个数的时候，扩展 BaseSubscriber 就很方便了。
 *
 * 扩展的时候通常至少要覆盖 hookOnSubscribe(Subscription subscription) 和 hookOnNext(T value) 这两个
 * 方法。这个例子中， hookOnSubscribe 方法打印一段话到标准输出，然后进行第一次请求。 然后 hookOnNext
 * 同样进行了打印，同时逐个处理剩余请求。
 * @param <T>
 */
public class SampleSubscriber<T> extends BaseSubscriber<T> {

    //hook
    //钩;钓钩;挂钩;鱼钩;钩拳;曲线球
    //v.
    //(使)钩住，挂住;(尤指用腿、胳膊、手指等)钩住，箍住;钓(鱼)

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println("subscription");
        request(1);
    }

    @Override
    protected void hookOnNext(T value) {
        System.out.println(value);
        request(1);
    }
}
