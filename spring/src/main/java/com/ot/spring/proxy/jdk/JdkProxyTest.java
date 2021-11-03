package com.ot.spring.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTest {

    private Worker worker;

    public JdkProxyTest(Worker worker) {
        this.worker = worker;
    }

    public JdkProxyTest() {
    }

    /**
     * 代理类，一定要代理的是目标对象
     *
     * @return
     */
    public Worker getInstance() {
        byte[] bytes = ProxyGenerator.generateProxyClass("AProxy", new Class[]{Worker.class});
        try {
            RandomAccessFile r = new RandomAccessFile(
                    new File(
                            "D:/java/project/learning/spring/src/main/java/com/ot/spring/proxy/jdk/AProxy.class"
                    ),
                    "rw");
            r.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Worker) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{Worker.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(worker, args);
                    }
                });
    }


    public static void main(String[] args) {
        JdkProxyTest jdkProxyTest = new JdkProxyTest(new ManWorker());
        Worker instance = jdkProxyTest.getInstance();
        String sss = instance.work("sss");
        System.out.println(sss);
    }
}
