package com.ot.jvm.day03;

/**
 * gcroots对象：1.虚拟机栈中引用的对象
 * 2.元空间中静态属性引用的对象
 * 3.元空间中常量引用的对象
 * 4.本地方法栈中jni引用的对象
 */
public class GCRoots {
    Object o = new Object();
    static Object GCRoot1 = new Object(); //GC Roots
    final static Object GCRoot2 = new Object();

    //
    public static void main(String[] args) {
        //可达
        Object object1 = GCRoot1; //=不是赋值，在对象中是引用，传递的是右边对象的地址
        Object object2 = object1;
        Object object3 = object1;
        Object object4 = object3;
    }

    public void fame() {
        //不可达（方法运行完后可回收）
        Object object5 = o;//o不是GCRoots
        Object object6 = object5;
        Object object7 = object5;
    }

    //本地变量表中引用的对象
    public void stack() {
        Object ostack = new Object();    //本地变量表的对象
        Object object9 = ostack;
        //以上object9 在方法没有(运行完)出栈前都是可达的
    }
}
