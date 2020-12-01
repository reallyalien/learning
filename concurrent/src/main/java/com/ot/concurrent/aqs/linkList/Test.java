package com.ot.concurrent.aqs.linkList;


public class Test {

    public static void main(String[] args) {
        DoubleLinkedList list = new DoubleLinkedList();
//        list.add("jack");
//        list.add("jack2");
//        list.add("jack3");
        list.remove("jack3");
        System.out.println(list);
    }
}
