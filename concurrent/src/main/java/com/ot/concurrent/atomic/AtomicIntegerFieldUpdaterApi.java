package com.ot.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 更新某个对象的字段，为确保线程安全
 * 1. AtomicIntegeFieldUpdater：原子更新整型字段类；
 * 2. AtomicLongFieldUpdater：原子更新长整型字段类；
 * 3. AtomicStampedReference：原子更新引用类型，这种更新方式会带有版本号。在更新
 * 的时候会带有版本号，是为了解决CAS的ABA问题；
 * <p>
 * <p>
 * 要想使用原子更新字段需要两步操作：
 * 1. 原子更新字段类都是抽象类，只能通过静态方法 newUpdater 来创建一个更新器，并且需要设置想
 * 要更新的类和属性；
 * 2. 更新类的属性必须使用 public volatile 进行修饰；
 */
public class AtomicIntegerFieldUpdaterApi {
    static AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    public static void main(String[] args) {
        User user = new User("Jack", 10);
        int i = updater.addAndGet(user, 20);
        int i2 = updater.get(user);
        System.out.println(i);
        System.out.println(i2);
    }

    static class User {
        private final String userName;
        public volatile int age;

        public User(String userName, int age) {
            this.userName = userName;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" + "userName='" + userName + '\'' + ", age=" + age + '}';
        }
    }
}