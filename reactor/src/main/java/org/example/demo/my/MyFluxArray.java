package org.example.demo.my;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MyFluxArray<T> extends MyFlux<T> {
    private T[] array;

    public MyFluxArray(T... data) {
        this.array = data;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new MyArraySubscription<T>(subscriber, array));
    }


    static class MyArraySubscription<T> implements Subscription {
        final Subscriber<? super T> actual;
        final T[] array;
        int index;
        boolean canceled;

        public MyArraySubscription(Subscriber<? super T> actual, T[] array) {
            this.actual = actual;
            this.array = array;
        }

        @Override
        public void request(long n) {
            if (canceled) {
                return;
            }
            int length = array.length;
            for (int i = 0; i < n && index < length; i++) {
                actual.onNext(array[index++]);
            }
            if (index == length) {
                actual.onComplete();
            }
        }

        @Override
        public void cancel() {
            this.canceled = true;
        }
    }
}
