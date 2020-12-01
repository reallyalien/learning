package com.ot.jvm.day03;

/**
 * @Title: Test
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/10
 * @Version: 1.0
 */
public class Test {

    public User user = new User("fame", 18);

    public static void main(String[] args) {
        User user = new User("fame", 18);

        //  多线程的启动

        System.gc();
    }

}

class User{
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}