package org.example.demo.create;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 回压的处理有以下几种策略：
 * 1. ERROR： 当下游跟不上节奏的时候发出一个错误信号。
 * 2. DROP：当下游没有准备好接收新的元素的时候抛弃这个元素。
 * 3. LATEST：让下游只得到上游最新的元素。
 * 4. BUFFER：缓存下游没有来得及处理的元素（如果缓存不限大小的可能导致
 * OutOfMemoryError）。
 * 这几种策略定义在枚举类型 OverflowStrategy 中，不过还有一个IGNORE类型，即完全忽略下游背压
 * 请求，这可能会在下游队列积满的时候导致 IllegalStateException。
 */
public class BackPress {

    MyEventSource source = new MyEventSource();


}
