package com.ot.sort;

import java.util.Arrays;

/**
 * 堆排序
 * 将树当中的树按层次遍历写入到数组
 * 左子节点：2n+1
 * 右子节点: 2n+2
 * 父节点：(n-1)/2
 * 大顶堆：每个节点的值都大于等于左右子节点的值  arr[i]>=arr[2*i+1]   arr[i]>=arr[2*i+2]
 * 小顶堆：每个节点的值都小于等于左右子节点的值  arr[i]<=arr[2*i+1]   arr[i]<=arr[2*i+2]
 * <p>
 * 一般升序使用大顶堆，降序使用小顶堆
 * <p>
 * 思想：
 * 1.将待排序的元素构造成一个大顶堆
 * 2.此时，整个序列的最大值就是堆顶的根节点
 * 3.将其与末尾元素交换，此时末尾就是最大值
 * 4.然后将剩余n-1个元素重新构造成一个大顶堆，这样会得到n个元素的次小值，如此反复进行，就能得到一个有序列
 */
public class HeapSorting {

    public static void main(String[] args) {
        //升序排列，调整为大顶堆
        int[] arr = {4, 6, 8, 5, 9};//此数组就是树当中按层次遍历的结果
        /**
         *          4
         *     6         8
         *  5    9
         */
        sort2(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
//        adjustHeap(arr, 1, 5);
//        System.out.println("第一次调整之后：" + Arrays.toString(arr));
//
//        adjustHeap(arr,0,5);
//        System.out.println("第二次调整之后：" + Arrays.toString(arr));


        //叶子节点不需要调整
        //这里的i，对于满二叉树来讲，都是从右边的第一个非叶子节点开始，然后依次-1，逆序遍历非叶子节点的那一层，
        //for循环结束之后，只找到了最大值，需要交换，然后依次进行对剩下的元素构造大顶堆
        //最大下标的元素的父节点 计算公式： (n-1)/2
        for (int i = (arr.length - 1 - 1) / 2; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
            System.out.println("第" + (i + 1) + "次调整之后：" + Arrays.toString(arr));
        }
        //上面的for循环只把最大的数放在的root节点，形成大顶堆，
        //并且循环结束之后，每个节点的数都比它的左右节点的数要大，因此下面交换完之后，需要从0这个位置调整
        for (int j = arr.length - 1; j > 0; j--) {
            //交换
            arr[j] = arr[j] ^ arr[0];
            arr[0] = arr[j] ^ arr[0];
            arr[j] = arr[j] ^ arr[0];
            //第一次交换完之后，最小的数位于堆顶，然后接下来每次都从0开始构造大顶堆
            adjustHeap(arr, 0, j);
        }
//
    }

    /**
     * 完成将以i对应的叶子节点的树调整为大顶堆
     * 将一个数组（二叉树）调整为大顶堆
     *
     * @param arr    需要调整的数组
     * @param i      非叶子节点在数组当中的索引
     * @param length 对多少个元素进行调整，length在逐渐减少
     *               4 6 8 5 9
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i];//先取出当前元素的值，保存在一个临时变量
        //开始调整 k,下一次继续调整左子节点，
        for (int k = 2 * i + 1; k < length; k = 2 * k + 1) {
            if ((k + 1) < length && arr[k] < arr[k + 1]) {
                //说明左子节点小于右子节点
                k++;//k指向右子节点，作为较大值
            }
            if (arr[k] > temp) {
                //arr[k]为较大值，子节点大于父节点，进行替换
                arr[i] = arr[k];//把较大值赋给父节点，
                i = k;//可以立即i指向上一次被交换的值的位置，i指向k继续循环比较，因为有可能你的temp的值小于前一次的调整过后的节点的值
            } else {
                break;
            }
        }
        //以i为非叶子节点的值已经是最大了
        arr[i] = temp;//将temp的值调整，此时的i已经不是原来的i，而是刚刚交换的k的位置
    }


    public static void sort2(int[] arr) {
        //这个for循环结束之后才构建了一个大顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjust(arr, i, arr.length);
        }
        //交换元素
        for (int i = arr.length - 1; i > 0; i--) {
            arr[i] = arr[i] ^ arr[0];
            arr[0] = arr[i] ^ arr[0];
            arr[i] = arr[i] ^ arr[0];
            adjust(arr, 0, i);
        }

    }

    public static void adjust(int[] arr, int i, int length) {
        int temp = arr[i];
        //找到要替换temp的值之后不必要急着替换，因为有可能替换完之后，你还得继续判断，这个已经替换的位置的值小于其左右子节点的值，
        //因此判断完子节点之后再替换就ok，省去中间无所谓的替换操作
        for (int k = 2 * i + 1; k < length; k = 2 * k + 1) {
            //判断当前节点i的右子节点是不是大于左子节点的值，
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k++;
            }
            //如果左子节点或者右子节点的值大于当前i的值，进行替换，并i指向k，也就是指向哪个位置替换的
            if (arr[k] > temp) {
                arr[i] = arr[k];
                i = k;
            }
        }
        arr[i] = temp;
    }

}
