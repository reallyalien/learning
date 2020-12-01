package com.ot.jvm.day05.object.item8;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 优先使用复合胜过继承   --  复合类
 * @Title: CompHashSet
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/21
 * @Version: 1.0
 */
public class CompHashSet<E>{
    private final Set<E> s;// 在新的类中添加一个私有域
    private int addCount = 0;//特殊需求：元素个数

    public CompHashSet(Set<E> s) {
        this.s = s;
    }
    //添加一个元素
    public boolean add(E e) {
        addCount++;
        return s.add(e);
    }
    //添加一组元素
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return s.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        CompHashSet<String> s = new CompHashSet<String>(new HashSet<String>());
        s.addAll(Arrays.asList("Fame", "coly", "zero"));
        System.out.println(s.getAddCount());
    }
}
