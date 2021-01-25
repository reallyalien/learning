package com.ot.jvm.day04.serviceloader;

import java.util.ServiceLoader;

public class Test {

    public static void main(String[] args) {
        //加载MATE-INF下services的同名接口文件的实现类
        ServiceLoader<IService> iServiceServiceLoader = ServiceLoader.load(IService.class);
        for (IService iService : iServiceServiceLoader) {
            //在迭代过程当中去反射创建实例
            System.out.println(iService.getScheme() + "=" + iService.sayHello());
        }
    }
}
