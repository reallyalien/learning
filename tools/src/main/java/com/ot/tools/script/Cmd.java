package com.ot.tools.script;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cmd {

    /**
     * public Process exec(String command)-----在单独的进程中执行指定的字符串命令。
     * <p>
     * public Process exec(String [] cmdArray)---在单独的进程中执行指定命令和变量
     * <p>
     * public Process exec(String command, String [] envp)----在指定环境的独立进程中执行指定命令和变量
     * <p>
     * public Process exec(String [] cmdArray, String [] envp)----在指定环境的独立进程中执行指定的命令和变量
     * <p>
     * public Process exec(String command,String[] envp,File dir)----在有指定环境和工作目录的独立进程中执行指定的字符串命令
     * <p>
     * public Process exec(String[] cmdarray,String[] envp,File dir)----在指定环境和工作目录的独立进程中执行指定的命令和变量
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
//        Process process = Runtime.getRuntime().exec("ipconfig");
        //导致当前进程等待，如果有必要，一直要等待process的进程结束，如果process已中止，此方法立即返回，0表示正常结束，在java当中，调用runtime
        //线程执行脚本是非常消耗资源的，在调用这个的时候，相当于jvm开了一个子线程去执行那个命令，其中开了3个通道：输入流，输出流，错误流
//        process.waitFor();
        //记事本打开之后如果不关闭，下面的语句不会执行
//        System.out.println("我被执行了");
//        execute("test.bat",null,"d:/");
    }

    public static void execute(String param) throws Exception {
        String command = param;
        int exitValue = -1;

        BufferedReader bufferedReader = null;
        try {
            // command process
            Process process = Runtime.getRuntime().exec(command);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(process.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            // command log
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            // command exit
            process.waitFor();
            exitValue = process.exitValue();
        } catch (Exception e) {

        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        if (exitValue == 0) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
    }
}
