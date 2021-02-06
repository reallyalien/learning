package org.example.demo.my;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * myFlux->myFluxArray->subscriber.onSubscribe->Subscription.request->subscriber.onNext
 */

public class MyFluxTest {

    @Test
    public void test1() {
        MyFlux<String> flux = MyFlux.just("A", "B", "C").map(i->i+"aaa");
        flux.subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(6);
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext:"+s);

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}
