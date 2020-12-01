package com.ot.jvm.day05.object.item8;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * 优先使用复合胜过继承   --  继承类：统计增加的个数
 * @Title: ExtendsHashSet
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/21
 * @Version: 1.0
 */
public class ExtendsHashSet<E> extends HashSet<E> {

    private int addCount = 0;//计数器（每加一个元素就+1）

    public ExtendsHashSet() {
    }

    public ExtendsHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;  //  调用了三次
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
//        addCount += c.size();  //3
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        ExtendsHashSet<String> s = new ExtendsHashSet<String>();
        s.addAll(Arrays.asList("Fame", "coly", "zero"));
        System.out.println(s.getAddCount());
        //  3
    }
}
