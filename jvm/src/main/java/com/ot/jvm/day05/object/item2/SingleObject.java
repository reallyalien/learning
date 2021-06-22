package com.ot.jvm.day05.object.item2;

import com.ot.jvm.day05.object.item5.Stack;

import java.io.Serializable;

public class SingleObject implements Serializable {

    private static SingleObject instance = new SingleObject();

    private SingleObject() {
    }

    //这种方式通过反射还可以创建对象
    //通过序列号和反序列化也可以创建对象
    public static SingleObject getInstance() {
        return instance;
    }


    /**
     * 因此外部无法通过构造器创建枚举类的实例，这也是枚举类通常用来保存常量的一个原因之一。
     * 其次，枚举类的实例会在类加载的时候线程安全的进行初始实例化，在类加载的时候，JVM会对()
     * 方法加锁，其他线程都需要阻塞等待，直到活动线程执行()方法完毕。需要注意的是，其他线程
     * 虽然会被阻塞，但如果执行()方法的那条线程退出()方法后，其他线程唤醒后不会再次进入()方法。
     * 同一个类加载器下，一个类型只会初始化一次。
     */
    static enum SingletonEnum {
        //创建一个枚举对象，天生线程安全
        INSTANCE;

        //枚举的构造方式是私有的，使用反射创建枚举对象时会报错
        private SingletonEnum() {
        }

        public SingletonEnum getInstance() {
            return INSTANCE;
        }
    }


}
