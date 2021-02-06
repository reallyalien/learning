package org.example.demo.my;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.Function;

public class MyFluxMap<T, R> extends MyFlux<R> {

    private final MyFlux<? extends T> source;
    private final Function<? super T, ? extends R> mapper;

    public MyFluxMap(MyFlux<? extends T> source, Function<? super T, ? extends R> mapper) {
        this.source = source;
        this.mapper = mapper;
    }


    @Override
    public void subscribe(Subscriber<? super R> actual) {
        source.subscribe(new MyMapSubscriber<>(actual, mapper));
    }

    /**
     * 对下游是作为发布者，传递上游的数据到下游，对上游是请阅者，传递下游的请求到上游
     * @param <T>
     * @param <R>
     */
    static class MyMapSubscriber<T, R> implements Subscriber<T>, Subscription {

        private final Subscriber<? super R> actual;
        private final Function<? super T, ? extends R> mapper;
        boolean done;
        Subscription subscription;

        public MyMapSubscriber(Subscriber<? super R> actual, Function<? super T, ? extends R> mapper) {
            this.actual = actual;
            this.mapper = mapper;
        }

        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscription=subscription;
            actual.onSubscribe(this);
        }

        @Override
        public void onNext(T t) {
            if (done){
                return;
            }
            actual.onNext(mapper.apply(t));
        }

        @Override
        public void onError(Throwable throwable) {
            if (done){
                return;
            }
            done=true;
            actual.onError(throwable);
        }

        @Override
        public void onComplete() {
            if (done){
                return;
            }
            done=true;
            actual.onComplete();
        }

        @Override
        public void request(long l) {
            this.subscription.request(l);
        }

        @Override
        public void cancel() {
            this.subscription.cancel();
        }
    }
    //=========================================================================================================================

    static final class MyMapSubscription<T, R> implements Subscription {

        private final Subscriber<? super R> actual;
        private final Function<? super T, ? extends R> mapper;

        public MyMapSubscription(Subscriber<? super R> actual, Function<? super T, ? extends R> mapper) {
            this.actual = actual;
            this.mapper = mapper;
        }

        @Override
        public void request(long l) {

        }

        @Override
        public void cancel() {

        }
    }


}
