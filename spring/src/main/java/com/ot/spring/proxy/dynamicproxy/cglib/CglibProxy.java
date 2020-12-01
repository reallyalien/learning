package com.ot.spring.proxy.dynamicproxy.cglib;

import com.ot.spring.proxy.dynamicproxy.impl.Me;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib通过继承的方式来代理目标类
 */
public class CglibProxy implements MethodInterceptor {
    private Me target;

    public Object getInstance(Me me) {
        this.target = me;
        //创建代理类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(me.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     * @param o           代理对象
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开始物色");
        methodProxy.invokeSuper(o, objects);
//        method.invoke(target, objects);
        System.out.println("object:" + o.getClass());//class com.ot.spring.proxy.dynamicproxy.impl.Me$$EnhancerByCGLIB$$20fa90c5
        System.out.println("MethodProxy:" + methodProxy.getClass());//class net.sf.cglib.proxy.MethodProxy
        System.out.println("如果合适，就马上办事");
        return null;
    }
}
