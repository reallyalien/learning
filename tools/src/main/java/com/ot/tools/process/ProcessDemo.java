package com.ot.tools.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class ProcessDemo {
    public static void main(String[] args) throws InterruptedException {
        Process process = null;
        try {
//            Process process = Runtime.getRuntime().exec("ping www.baidu.com");
            process = Runtime.getRuntime().exec("java -version1111");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "gbk"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("任务执行完毕！");
            boolean b = process.waitFor(10, TimeUnit.SECONDS);
            System.out.println("try->" + b);
            int i = process.exitValue();
            System.out.println("try->" + i);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            boolean b = process.waitFor(10, TimeUnit.SECONDS);
            System.out.println("catch->" + b);
            int i = process.exitValue();
            System.out.println("catch->" + i);
        }
    }

}
