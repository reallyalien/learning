package com.ot.spring.proxy.dynamicproxy.jdk;

import com.ot.spring.proxy.dynamicproxy.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy implements InvocationHandler {

    //目标对象（被代理对象）
    private Person target;

    /**
     * @param target 被代理对象，（目标对象）
     * @return 生成的代理对象
     */
    public Object getInstance(Person target) {
        this.target = target;
        Class<?> clazz = target.getClass();
        //通过反射生成目标对象()字节码重组
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    /**
     * @param proxy  生成的代理对象
     * @param method 目标对象的方法
     * @param args   目标方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理对象方法执行前");
        System.out.println("==============================================================");
        System.out.println("proxy:" + proxy.getClass());
        //执行目标对象的方法
        String invoke = (String) method.invoke(target, args);
        System.out.println("方法放行之后的结果：" + invoke);
        System.out.println("==============================================================");
        System.out.println("代理对象方法执行后");
        return null;
    }
}
