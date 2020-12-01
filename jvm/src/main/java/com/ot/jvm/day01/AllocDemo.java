package com.ot.jvm.day01;

public class AllocDemo {
    class User {
        int id;
        String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    private void alloc(int i) {
        User user = new User(i, "name" + i);
        //System.out.println(user);
    }

    public static void main(String[] args) {
        AllocDemo a = new AllocDemo();
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            a.alloc(i);
        }
        long s2 = System.currentTimeMillis();
        System.out.println("time:" + (s2 - s1));//记录分配时间
    }
}