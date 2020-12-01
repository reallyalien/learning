package com.ot.jvm.day05.object.item2;

import java.io.*;

/**
 * 通过序列化的方式获取到的也不是同一个对象
 */
public class SerializabledDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Singleton singleton1 = Singleton.getInstance();
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("d:/1.txt"));
        oos.writeObject(singleton1);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("d:/1.txt"));
        Singleton singleton2 = (Singleton) ois.readObject();
        System.out.println(singleton1.hashCode());
        System.out.println(singleton2.hashCode());
    }
}
