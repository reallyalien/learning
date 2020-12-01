package com.ot.jvm.day05.object.item5;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 消除过期的对象引用    --  自定义栈
 */
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 1;

    /**
     * 栈初始化
     */
    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    /**
     * 压栈
     * @param e
     */
    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    /**
     * 出栈
     * @return
     */
    public Object pop() {
        if(size == 0)
            throw new EmptyStackException();
        elements[size] = null;  //  清除对象引用  // 被错误的调用  NullPointerExcetion
        Object element = elements[0];
        elements[0]=null;
        size--;
        return element;
    }

    /**
     * 该方法用于判断数组空间中是否至少有一个空余空间，如果没有就对数组进行扩容
     */
    private void ensureCapacity() {
        if(elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }
}
