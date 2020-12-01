package com.ot.jvm.day01;

public class ConstantPool {

    public static void main(String[] args) {
       String a="a";
       String b="ab";
       String c=a+b;
       String d="aab";
       System.out.println(c.intern()==d);
       System.out.println(a.intern()==a);

    }
}
