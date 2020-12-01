package com.ot.jvm.day04.clazzload;

/**
 * 被动使用类字段演示一：通过子类引用父类的静态字段，不会导致子类初始化
 * @Title: NotInitialization
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/13
 * @Version: 1.0
 */
public class NotInitialization {
    public static void main(String[]args){
        //直接打印子类中的 静态变量:
        //如果通过子类引用父类中的静态字段，只会触发父类的初始化，而不会触发子类的初始化
        //System.out.println(SubClaszz.value);
        //使用数组的方式， 会不会打印初始化
        //SuperClazz[]sca = new SuperClazz[10];


        //打印 一个常量(会不会进行初始化， 1，不会2)
        //常量池\ class编译后，常量池的信息也放入class类中
        //System.out.println(SuperClazz.HELLOWORLD);//hello world写入、

        //如果使用常量去引用另外一个常量(会不会进行初始化， 1，不会2)
        System.out.println(SuperClazz.WHAT);


        //System.out.println(SubClaszz.value);
        //SuperClazz[]sca = new SuperClazz[10];

        //System.out.println(SuperClazz.HELLOWORLD);
        //System.out.println(SuperClazz.WHAT);

    }
}
