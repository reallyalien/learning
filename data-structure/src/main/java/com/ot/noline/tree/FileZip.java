package com.ot.noline.tree;

import java.io.*;
import java.util.Map;

/**
 * 文件压缩
 */
public class FileZip {

    public static void main(String[] args) throws IOException {
        String srcFile = "D:/1.txt";
        String descFile = "D:/1.zip";
        zip(srcFile, descFile);
        //==============================
        String srcFile1 = "D:/1.zip";
        String descFile1 = "D:/2.txt";
        dezip(srcFile1, descFile1);
    }

    /**
     * 解压文件
     * @param srcFile  源文件
     * @param descFile 目标文件
     */
    public static void dezip(String srcFile, String descFile) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        ObjectInputStream ois = null;
        try {
            is = new FileInputStream(srcFile);
            ois = new ObjectInputStream(is);
            //读取数组
            byte[] data = (byte[]) ois.readObject();
            //读取霍夫曼编码表
            Map<Character, String> map = (Map<Character, String>) ois.readObject();
            //解码
            String dataStr = HuffManEncode.decode(data, map);
            //将dataStr写入到目标文件
            os = new FileOutputStream(descFile);
            os.write(dataStr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
            os.close();
            ois.close();
        }
    }


    /**
     * 压缩文件
     *
     * @param srcFile  准备压缩的文件
     * @param descFile 压缩到哪里
     * @throws IOException
     */
    public static void zip(String srcFile, String descFile) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            is = new FileInputStream(srcFile);
            byte[] data = new byte[is.available()];
            //读取文件
            is.read(data);
            //获取文件对应的霍夫曼编码表
            byte[] encode = HuffManEncode.encode(new String(data));
            os = new FileOutputStream(descFile);
            //ObjectOutputStream
            oos = new ObjectOutputStream(os);
            //这里以对象流的方式写入压缩文件
            oos.writeObject(encode);
            //将编码表也写入文件,否则恢复不了
            oos.writeObject(HuffManEncode.huffManCodes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
            os.close();
            oos.close();
        }
    }
}
