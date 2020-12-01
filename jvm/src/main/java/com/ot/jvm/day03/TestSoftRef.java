package com.ot.jvm.day03;//package com.jd.jvm.day03;
//
//import java.lang.ref.SoftReference;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * 软引用
// * @Title: TestSoftRef
// * @Description: TODO
// * @Author: 六星教育     Fame老师
// * @CreateDate: 2020/4/10
// * @Version: 1.0
// */
//public class TestSoftRef {
//    //对象
//    public static class User{
//        public int id = 0;
//        public String name = "";
//        public User(int id, String name) {
//            super();
//            this.id = id;
//            this.name = name;
//        }
//        @Override
//        public String toString() {
//            return "User [id=" + id + ", name=" + name + "]";
//        }
//
//    }
//    //
//    public static void main(String[] args) {
//        User u = new User(1,"Fame"); //new是强引用
//        SoftReference<User> userSoft = new SoftReference<User>(u);
//        u = null;//干掉强引用，确保这个实例只有userSoft的软引用
//        System.out.println(userSoft.get()); //看一下这个对象是否还在
//        System.gc();//进行一次GC垃圾回收  千万不要写在业务代码中。
//        System.out.println("After gc");
//        System.out.println(userSoft.get());
//        //往堆中填充数据，导致OOM
//        List<byte[]> list = new LinkedList<>();
//        try {
//            for(int i=0;i<100;i++) {
//                System.out.println("*************"+userSoft.get());
//                list.add(new byte[1024*1024*1]); //1M的对象
//            }
//        } catch (Throwable e) {
//            //抛出了OOM异常时打印软引用对象
//            System.out.println("Exception*************"+userSoft.get());
//        }
//
//    }
//}
