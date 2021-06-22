package com.ot.tools.script;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Cmd1 {


    public static void main(String[] args) throws Exception {
//        String[] cmd = new String[] { "cmd.exe", "/C", "wmic process get name" };
//        String[] cmd = new String[] { "cmd.exe", "/C","net config workstation" };
        String[] cmd = new String[] { "cmd.exe","/C","wmic useraccount list full" };
        int exitValue = -1;

        BufferedReader bufferedReader = null;
        try {
            // command process
            Process process = Runtime.getRuntime().exec(cmd);
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
