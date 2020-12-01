package com.ot.spring.proxy.staticproxy.impl;

import com.ot.spring.proxy.staticproxy.Person;

public class Son implements Person {
    @Override
    public void findLove() {
        System.out.println("子类的方法执行");
    }


}
