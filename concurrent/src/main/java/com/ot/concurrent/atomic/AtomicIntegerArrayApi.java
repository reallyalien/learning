package com.ot.concurrent.atomic;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

/*
1. addAndGet(int i, int delta)：以原子更新的方式将数组中索引为i的元素与输入值相加；
2. getAndIncrement(int i)：以原子更新的方式将数组中索引为i的元素自增加1；
3. compareAndSet(int i, int expect, int update)：将数组中索引为i的位置的元素进行更新
 */
public class AtomicIntegerArrayApi {

    static int[] array = {1, 2, 3, 4, 5, 6, 7};
    static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(array);

    public static void main(String[] args) {
        //对数组中索引为1的元素+10
        int i = atomicIntegerArray.addAndGet(1, 10);
        System.out.println(i);
        System.out.println(atomicIntegerArray);
        //原数组没有变化
        System.out.println(Arrays.toString(array));
    }
}
