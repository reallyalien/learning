package com.ot.jvm.day04.load.cap01;

import java.io.IOException;
import java.io.InputStream;

/**
 * 这里我们重写了loadClass方法，打破了双亲委派模型，故这里的类由我们自定义的类加载器去加载
 * 若不想打破双亲委派模型，则重写findClass方法，loadClass方法会自动调用我们的findclass方法
 */
public class MyLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
            InputStream is = getClass().getResourceAsStream(fileName);

            //必须这样写，要想加载我们自定义的这个类，需要先去加载object，classloader这些父类，最后才能加载我们自己定义的类,
            //而object和classloader这些父类需要父加载器才能去加载
            //如果当前的文件没有被加载，则调用父类的类加载器去加载，
            if (is == null) {
                return super.loadClass(name);
            }
            //获取与读取文件相同大小的流字节
            byte[] b = new byte[is.available()];
            is.read(b);
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }
}
