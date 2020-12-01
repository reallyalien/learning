package com.ot.concurrent.a;

import java.util.*;
import java.util.stream.Collectors;

public class Man {

    private int age;
    private Date birthday;
    private String name;
    private List<Integer> list = new ArrayList<>();

    public Man() {
    }

    public Man(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Man man = (Man) o;
//        return age == man.age &&
//                Objects.equals(name, man.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(age, name);
//    }

    //    public static void main(String[] args) throws InterruptedException {
//        List<Man> list=new ArrayList<>();
//        list.add(new Man(19,new Date()));
//        Thread.sleep(1000);
//        list.add(new Man(18,new Date()));
//        Thread.sleep(1000);
//        list.add(new Man(20,new Date()));
//        List<Man> collect = list.stream().sorted(new Comparator<Man>() {
//            @Override
//            public int compare(Man o1, Man o2) {
//                return o2.getBirthday().compareTo(o1.getBirthday());
//            }
//        }).collect(Collectors.toList());
//        List<Man> collect1 = list.stream().sorted(((o1, o2) -> o2.getBirthday().compareTo(o1.getBirthday()))).collect(Collectors.toList());
//        System.out.println(collect);
//        System.out.println(collect1);
//
//    }
    public static void main(String[] args) throws InterruptedException {
        Man man1 = new Man(10, "jack");
        Man man2 = new Man(10, "jack");
        HashSet<Man> set = new HashSet<>();
        set.add(man1);
        set.add(man2);
        System.out.println(set.size());
    }
}
