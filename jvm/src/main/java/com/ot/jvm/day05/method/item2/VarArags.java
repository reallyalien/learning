package com.ot.jvm.day05.method.item2;

/**
 * 可变参数要谨慎使用
 * @Title: VarArags
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/21
 * @Version: 1.0
 */
public class VarArags {
    //累加方法：可变参数 0~很多
    static int sum(int... args) {
        int sum = 0;
        for (int arg : args){
            sum += arg;
        }
        return sum;
    }

    //累加方法：可变参数 1~很多
    static int sum1(int first ,int... args) {
        int sum = 0;
        //if(first)
        if(args.length==0){
            //异常处理
        }
        for (int arg : args)
            sum += arg;
        return sum;
    }

    public static void main(String[] args) {
        //  array[] 个数
        //  把参数放入到数组
        //  把数组传给方法

        System.out.println(sum(1,2,3,4,5,6,7,8,9,10));
        System.out.println(sum(1,2,3,4,5,6));
        System.out.println(sum(null));
    }

//    public void sum() {}
//    public void sum(int a1) {}
//    public void sum(int a1, int a2) {}
//    public void sum(int a1, int a2, int a3) {}
//    public void sum(int a1, int a2, int a3, int... rest) {}
}
