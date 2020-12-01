package com.ot.spring.proxy.dynamicproxy.jdk;

import com.ot.spring.proxy.dynamicproxy.Person;
import com.ot.spring.proxy.dynamicproxy.impl.Me;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

public class TestProxy {


    @Test
    public void jdk(){
        //创建Me的代理对象
        Person obj = (Person) new JdkProxy().getInstance(new Me());
        System.out.println(obj.getClass());
        obj.findWork();


        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy()", new Class[]{Person.class});
        try {
            FileOutputStream fos=new FileOutputStream("E:\\Java\\project\\learning\\spring\\src\\main\\java\\com\\ot\\spring\\proxy\\dynamicproxy\\proxy.class");
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态代理通过字节码重组的方式去创建代理类，代理类继承proxy和实现目标对象实现的接口。通过反射获取接口的方法
     */
    @Test
    public void proxy(){
        byte[] bytes = ProxyGenerator.generateProxyClass("ProxyClazz", new Class[]{Person.class});
        try {
            FileOutputStream fos=new FileOutputStream("E:\\Java\\project\\learning\\spring\\src\\main\\java\\com\\ot\\spring\\proxy\\dynamicproxy\\ProxyClazz.class");
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
