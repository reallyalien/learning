package com.ot.spring.proxy.jdk;


public class ManWorker implements Worker {

    @Override
    public String work(String str) {
        System.out.println("ManWorker -->" + str);
        return "我是实现类";
    }
}
