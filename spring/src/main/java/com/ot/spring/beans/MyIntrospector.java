package com.ot.spring.beans;

import com.sun.scenario.effect.impl.sw.java.JSWPerspectiveTransformPeer;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * java内省类
 * 还会将当前类的父类的方法继承下来，进行获取，Object当中有一个getClass方法，也会获取到
 * 属性一定要小写，否则不按java约定来写，会导致找不到方法
 */
public class MyIntrospector {

    public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Cat cat = new Cat();
        BeanInfo beanInfo = Introspector.getBeanInfo(Cat.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            //获取set方法
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (propertyDescriptor.getName().equals("age")){
                //invoke属性注入
                writeMethod.invoke(cat,10);
            }
            System.out.println(propertyDescriptor);
        }
        System.out.println(cat);
    }
}
