package com.ot.jvm.day04.load.cap01;

import java.io.*;

public class CustomClassLoader extends ClassLoader {
    private String classLoaderName;
    private String classPath; //类加载器的加载路径
    private static final String suffix = ".class";

    public CustomClassLoader(){}

    public CustomClassLoader(String classLoaderName){
        super();//将系统类加载器当做该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }

    public CustomClassLoader(ClassLoader parent, String classLoaderName){
        super(parent);//显示指定该类加载器的父加载器
        this.classLoaderName = classLoaderName;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /*
        classloader中的loadClass会调用findclass方法，此方法被调用，说明我们自定义的类加载器生效
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("自定义类加载器【" + this.classLoaderName + "】查找类" + name);
        byte[] bytes = this.loadClassData(name);
        return this.defineClass(name,bytes,0,bytes.length);
    }

    private byte[] loadClassData(String className){
        InputStream is=null;
        byte[] data=null;
        ByteArrayOutputStream bos=null;
        className=className.replace(".","/");
        try {
            is=new FileInputStream(new File(this.classPath+className+suffix));
            bos=new ByteArrayOutputStream();
            byte[] buffer=new byte[8192];
            int len=0;
            while ((len=is.read(buffer))!=-1){
                bos.write(buffer,0,len);
            }
            data=bos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader parent = ClassLoader.getSystemClassLoader().getParent();
        CustomClassLoader customerClassLoader = new CustomClassLoader(parent,"customerClassLoader");
        customerClassLoader.setClassPath("e:/classes/");
        Class<?> aClass = customerClassLoader.loadClass("Test");
        System.out.println("当前的类加载器："+aClass.getClassLoader());
    }
}
