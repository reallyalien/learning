package com.ot.jvm.day04;

import java.util.Scanner;

/**
 * @Title: Fame
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/11
 * @Version: 1.0
 */
public class Fame {
    public static void fun(){
        fun();
    }
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] array = new int[n];
        for(int i=0; i<n; ++i)
            array[i] = scan.nextInt();

        for(int i=0; i<n; ++i)
            System.out.print(array[i] + " ");
        System.out.println();

        //array[n+1] = 0;
        //fun();
    }
}
