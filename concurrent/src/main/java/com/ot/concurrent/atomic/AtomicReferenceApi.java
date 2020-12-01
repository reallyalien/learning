package com.ot.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/*
1. AtomicReference：原子更新引用类型；
2. AtomicReferenceFieldUpdater：原子更新引用类型里的字段；
3. AtomicMarkableReference：原子更新带有标记位的引用类型
 */
public class AtomicReferenceApi {
    static AtomicReference<UserInfo> atomicReference;

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("jack", 10);
        atomicReference = new AtomicReference<>(userInfo);

        UserInfo updateUserInfo = new UserInfo("rose", 20);
        atomicReference.compareAndSet(userInfo, updateUserInfo);

        System.out.println(atomicReference.get());
    }

    static class UserInfo {
        private final String name;
        private final int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "UserInfo{" + "name='" + name + '\'' + ", age=" + age + '}';
        }

    }
}