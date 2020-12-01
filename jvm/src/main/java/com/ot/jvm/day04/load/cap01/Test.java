package com.ot.jvm.day04.load.cap01;

import com.sun.org.apache.bcel.internal.util.ClassPath;

/**
 * @Title: Test
 */
public class Test
{

    static int i=1;
    static
    {
        i=0;
//		System.out.println(i);
    }

    public static void main(String args[])
    {
        System.out.println("11");
    }
}