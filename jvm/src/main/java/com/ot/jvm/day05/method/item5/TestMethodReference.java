package com.ot.jvm.day05.method.item5;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @Title: TestMethodReference
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/21
 * @Version: 1.0
 */
public class TestMethodReference {

    public static void main(String[] args) {
        TestMethodReference.test01();
    }

    public static void test01(){
        Person[] pArr = new Person[]{
                new Person("003", LocalDate.of(2016,9,1)),
                new Person("001", LocalDate.of(2016,2,1)),
                new Person("002", LocalDate.of(2016,3,1)),
                new Person("004", LocalDate.of(2016,12,1))};

        // 使用匿名类
        Arrays.sort(pArr, new Comparator<Person>() {
            //  内部函数接口  可以使用 Lambda表达式
            @Override
            public int compare(Person a, Person b) {
                return a.getBirthday().compareTo(b.getBirthday());
            }
        });

        System.out.println(Arrays.asList(pArr));
    }

    public static void test02(){
        Person[] pArr = new Person[]{
                new Person("003", LocalDate.of(2016,9,1)),
                new Person("001", LocalDate.of(2016,2,1)),
                new Person("002", LocalDate.of(2016,3,1)),
                new Person("004", LocalDate.of(2016,12,1))};

        //使用lambda表达式  调用未有得方法
        Arrays.sort(pArr, (Person a, Person b) -> {
            return a.getBirthday().compareTo(b.getBirthday());
        });

        System.out.println(Arrays.asList(pArr));
    }

    public static void test03(){
        Person[] pArr = new Person[]{
                new Person("003", LocalDate.of(2016,9,1)),
                new Person("001", LocalDate.of(2016,2,1)),
                new Person("002", LocalDate.of(2016,3,1)),
                new Person("004", LocalDate.of(2016,12,1))};

        //使用lambda表达式和类的静态方法  调用已有得方法
        Arrays.sort(pArr, (a ,b) -> Person.compareByAge(a, b));

        System.out.println(Arrays.asList(pArr));
    }

    public static void test04(){
        Person[] pArr = new Person[]{
                new Person("003", LocalDate.of(2016,9,1)),
                new Person("001", LocalDate.of(2016,2,1)),
                new Person("002", LocalDate.of(2016,3,1)),
                new Person("004", LocalDate.of(2016,12,1))};

        //使用方法引用，引用的是类的静态方法
        Arrays.sort(pArr, Person::compareByAge);

        System.out.println(Arrays.asList(pArr));
    }
}
