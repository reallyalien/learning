package com.ot.line.chain;

import java.util.Arrays;

public class A {

    public static void main(String[] args) {
        int[] arr = new int[41];
        int per = 3;
        //这种是采用插值法，count是要插入的值，pos是当前要插入的位置，i是一个计数使用的
        for (int count = 1, pos = -1, i = 0; count <= 41; count++) {
            do {
                pos = (pos + 1) % 41;
                //循环完一圈之后，遇到不是0就相当于要跳过，因为这个地方已经有人了，因此得找下一个位置插入
                if (arr[pos] == 0) {
                    i++;
                }
                if (i == per) {
                    i = 0;
                    break;
                }
            } while (true);
            arr[pos] = count;
        }
        System.out.println(Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 40 || arr[i] == 41) {
                System.out.println(i + 1);
            }
        }
    }
}
