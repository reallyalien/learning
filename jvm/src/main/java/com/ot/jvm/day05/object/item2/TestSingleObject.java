package com.ot.jvm.day05.object.item2;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestSingleObject {

    public static void main(String[] args) {
        try {
            //反射对象
            Class<SingleObject> clazz = SingleObject.class;
            Constructor<SingleObject> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            SingleObject singleObject = declaredConstructor.newInstance();
            System.out.println(singleObject == SingleObject.getInstance());

            //===========================================
            //序列化对象
            SingleObject instance = SingleObject.getInstance();
            byte[] serialize = SerializationUtils.serialize(instance);
            Object deserialize = SerializationUtils.deserialize(serialize);
            System.out.println(instance == deserialize);


            //测试枚举
            Class<SingleObject.SingletonEnum> singletonEnumClass = SingleObject.SingletonEnum.class;
            Constructor<SingleObject.SingletonEnum> enumClassDeclaredConstructor = singletonEnumClass.getDeclaredConstructor();
            enumClassDeclaredConstructor.setAccessible(true);
            SingleObject.SingletonEnum singletonEnum = enumClassDeclaredConstructor.newInstance();
            System.out.println(singletonEnum == SingleObject.SingletonEnum.INSTANCE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
