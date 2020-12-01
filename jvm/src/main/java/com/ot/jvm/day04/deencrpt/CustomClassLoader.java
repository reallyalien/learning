package com.ot.jvm.day04.deencrpt;

import java.io.File;

/**
 * 类说明：自定义的类加载器,进行异或解密。URL
 * @Title: CustomClassLoader
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/13
 * @Version: 1.0
 */
public class CustomClassLoader extends ClassLoader{

    private final String name;
    private String basePath;
    private final static String FILE_EXT = ".class";

    public CustomClassLoader(String name) {
        super();
        this.name = name;
    }
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    private byte[] loadClassData(String name){
        byte[] data = null;
        XorEncrpt demoEncryptUtil = new XorEncrpt();
        try {
            String tempName = name.replaceAll("\\.","\\\\");
            //解密
            data = demoEncryptUtil.decrypt(new File(basePath+tempName+FILE_EXT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

//    @Override
//    public Class<?> loadClass(String name) throws ClassNotFoundException {
//        if(name.indexOf("java.")<5&&name.indexOf("java.")>-1){
//            return super.loadClass(name);
//        }
//        byte[] data = this.loadClassData(name);
//        if (data == null){
//            return super.loadClass(name);
//        }
//        return this.defineClass(name,data,0,data.length);
//    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name,data,0,data.length);
    }
}
