package com.ot.jvm.day05.method.item6;

import java.time.LocalDate;

/**
 * @Title: Teacher
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/22
 * @Version: 1.0
 */
public class Teacher<A> {
    private A teacher;

    public Teacher(A t) {
        teacher = t;
    }

    public static <T> T println(T t){
        System.out.println(t);
        return t;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacher=" + teacher +
                '}';
    }
}
