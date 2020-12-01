package com.ot.spring.proxy.dynamicproxy.impl;


import com.ot.spring.proxy.dynamicproxy.Person;

public class Me implements Person {

    @Override
    public String findWork() {
        System.out.println("me找工作");
        return "找到工作了";
    }
}
