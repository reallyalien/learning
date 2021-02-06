package org.example.demo.my;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.Function;


/**
 * 自定义的 flux,在reactor当中，flux既是一个发布者，又充当了工具类的角色,当我们使用just，range等工厂方法生成flux时
 * 会new一个新的flux，比如flux.just()会返回一个fluxArray对象
 *
 * @param <T>
 */
public abstract class MyFlux<T> implements Publisher<T> {
    @Override
    public abstract void subscribe(Subscriber<? super T> subscriber);

    public static <T> MyFlux<T> just(T... data) {
        return new MyFluxArray<T>(data);
    }

    public <V> MyFlux<V> map(Function<? super T, ? extends V> mapper) {
        return new MyFluxMap<T, V>(this, mapper);
    }
}
