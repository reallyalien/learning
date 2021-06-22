package com.ot.jvm.day05.design.item1;

/**
 * 枚举类命名
 * 枚举类的命名通常需要Enum为后缀，枚举成员名称需要全大写，单词间用下划线隔开。
 *
 * 枚举类不允许使用 extends 关键字
 * 枚举类默认会继承java.lang.Enum，由于java是单继承，所以在定义枚举类时不允许再继承其他类，但可以实现多个接口
 *
 * 枚举的比较可以直接使用 ==
 * 枚举是不允许被new出来的，枚举类里的构造函数都限定为私有化，是不允许使用public定义构造函数的。枚举的赋值，
 * 如果是同一个元素，则会指向同一个地址，所以是可以直接使用==的，当然在Enum类中，重写了equals方法（如下图），所以也是可以用equals来判断。
 */
public enum ConstantEnum {
    X(200, "成功"),
    Y(500,"失败");

    private int code;
    private String msg;

    private ConstantEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static void main(String[] args) {
        //枚举常用方法
        ConstantEnum[] values = ConstantEnum.values();
        //返回枚举常量名称
        System.out.println(ConstantEnum.X.toString());
        System.out.println(ConstantEnum.X.name());

    }
}
