package com.ot.tools.script;

public class A {

    public static void main(String[] args) {
        String bashCommand = "sh "+ "/usr/local/java/jdk1.8.0_121/lib/stopffmpeg.sh" + " ffmpeg " + 1001;
        System.out.println(bashCommand);
    }
}
