package com.ot.find;

import java.util.Arrays;

/**
 * 斐波拉契算法 f(k)=f(k-1)+f(k-2)
 * <p>
 * f(k)-1=f(k-1)-1+(f(k-2)-1)+1
 * <p>
 * mid=low+f(k-1)-1
 */
public class FibSearch {

    public static int maxSize = 20;

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        int search = search(arr, 1);
        System.out.println(search);
    }

    //需要先获取到一个斐波拉契数列
    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 2;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    /**
     * @param arr 数组
     * @param key 需要查找的数
     * @return 返回对应的下标
     */
    public static int search(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;
        int k = 0;//表示斐波拉契分割数值的下标
        int mid = 0;//存放mid值
        int[] f = fib();
        //获取到k
        while (high > f[k] - 1) {
            k++;
        }
        //因为f[k]可能大于arr的长度，因此我们需要构造一个新的数组指向arr
        int[] temp = Arrays.copyOf(arr, f[k]);
        //需要使用arr的最大值进行填充temp，因为temp得有序
        for (int i = high; i < temp.length; i++) {
            temp[i] = arr[high];
        }
        while (low <= high) {
            mid = low + f[k - 1] - 1;
            if (key < temp[mid]) {
                //我们应该继续向左边查找
                high = mid - 1;
                //全部元素
                k--;
            } else if (key > temp[mid]) {
                //向右边查找
                low = mid + 1;
                k -= 2;
            } else {
                //需要确定返回的下标
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        return -1;
    }
}
