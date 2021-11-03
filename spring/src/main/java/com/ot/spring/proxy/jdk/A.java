package com.ot.spring.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class A extends Proxy implements Worker {


    /**
     * Constructs a new {@code Proxy} instance from a subclass
     * (typically, a dynamic proxy class) with the specified value
     * for its invocation handler.
     *
     * @param h the invocation handler for this proxy instance
     * @throws NullPointerException if the given invocation handler, {@code h},
     *                              is {@code null}.
     */

    protected A(InvocationHandler h) {
        super(h);
    }

    @Override
    public String work(String str) {
        return null;
    }
}
