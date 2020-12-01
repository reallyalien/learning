package com.ot.concurrent.aqs.list;

public class ListTest {

    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(0,"jack");
        list.add(0,"jack1");
        list.add(0,"jack2");
        list.remove(0);
        System.out.println(list);
        System.out.println(list.contains("jack"));
    }
}
