package com.ot.jvm.day04.deencrpt;

import java.io.*;

/**
 * @Title: XorEncrpt
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/13
 * @Version: 1.0
 */
public class XorEncrpt{
    //位运算：异或操作（一个数经过两次异或=自己）^
    private void xor(InputStream in, OutputStream out)  throws Exception{
        int ch;
        while (-1 != (ch = in.read())){
            ch = ch^ 0xff;
            out.write(ch);
        }
    }
    //加密方法（流的方式），加密后重新写入
    public void encrypt(File src, File des) throws Exception {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(des);

        xor(in,out);

        in.close();
        out.close();
    }
    //解密方法，返回解密后的二进制数组
    public byte[] decrypt(File src) throws Exception {
        InputStream in = new FileInputStream(src);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        xor(in,bos);
        byte[] data = bos.toByteArray();;
        return data;
    }

    public static void main(String[] args) throws Exception {
        File src = new File("D:\\work\\ref-jvm\\bin\\com\\jvm\\ch04\\deencrpt\\DemoUserScr.class");
        File dest = new File("D:\\work\\ref-jvm\\bin\\com\\jvm\\ch04\\deencrpt\\DemoUser.class");
        XorEncrpt demoEncryptUtil = new XorEncrpt();
        demoEncryptUtil.encrypt(src,dest);
        System.out.println("加密完成！");
    }


}
