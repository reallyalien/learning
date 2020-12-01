package com.ot.jvm.day05.method.item6;

import java.time.LocalDate;
import java.util.*;

/**
 * 优先使用泛型方法
 * @Title: GenericityDemo
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/21
 * @Version: 1.0
 */
public class GenericityDemo {
    //原生类型方法
    public Set union1(Set set1, Set set2){
        Set set = new HashSet(set1);
        set.addAll(set2);
        return set;
    }

    //泛型方法
    public <E> Set<E> union2(Set<E> set1,Set<E> set2){
        Set<E> set = new HashSet<E>(set1);
        set.addAll(set2);
        return set;
    }

    public void show(){
        // TODO Auto-generated method stub
        GenericityDemo demo = new GenericityDemo();
        Set<String> firstSet = new HashSet<String>(
                Arrays.asList("Bady","Tim","Alice"));
        Set<String> secondSet = new HashSet<String>(
                Arrays.asList("Come","On","Go","With","Me"));
        Set<String> set = demo.union2(firstSet, secondSet);
        System.out.println(set);
    }


    public static List addMonthDays(){
        List<LocalDate> monthDays = new ArrayList<LocalDate>();
        monthDays.add(LocalDate.now().withDayOfMonth(1));
        monthDays.add(LocalDate.now().withDayOfMonth(2));
        monthDays.add(LocalDate.now().withDayOfMonth(3));
    //    monthDays.add(new Date());
        return monthDays;
    }

    public static void testList(){
        List<String> arrayList = new ArrayList<String>();
        arrayList.add("aaaa");
        arrayList.add("100");

        for(int i = 0; i< arrayList.size();i++){
            String item = (String)arrayList.get(i);
            System.out.println("泛型测试 item = " + item);//异常
        }
    }

    public static void main(String[] args) {
//        List monthDays = addMonthDays();
//        for(Object day : monthDays){
//            if(day instanceof Date){
//                Date date = (Date)day;//    2020/04/21
//                System.out.println(LocalDate.of(date.getYear(), date.getMonth(), date.getDay()));
//            } else {
//                System.out.println(day);
//            }
//        }
     //   GenericityDemo.testList();

        //  框架  顶层
        Teacher<Integer> teacher = new Teacher<Integer>(1000);
        System.out.println(teacher);
        Integer in = teacher.println(123456);
        String ss = teacher.println("fame");
    }
}
