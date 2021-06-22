package com.ot.concurrent.await;

import java.util.ArrayList;
import java.util.List;

public class A {

    private List<Integer> list = new ArrayList<>();

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public static void main(String[] args) {
        A a = new A();
        List<Integer> list = a.getList();
        list.add(1);
        list.add(2);
    }
}
