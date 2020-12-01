package com.ot.line.chain;

public class Josephus {
    //4 10 1 7 5 2 11 9 3 6 8
    public static int[] arrayOfJosephus(int number, int per) {
        int[] man = new int[number];
        //pos是当前元素的位置，count是要插入的数，一直在找空插入，每隔3下插入一次
        for (int count = 1, i = 0, pos = -1;
             count <= number; count++) {
            do {
                pos = (pos + 1) % number;  // 环状处理
                if (man[pos] == 0) i++;
                if (i == per) {  // 报数为3了
                    i = 0;
                    break;
                }
            } while (true);
            man[pos] = count;
            System.out.println("死亡编号："+pos);
        }
        return man;
    }

    public static void main(String[] args) {

        int[] man = Josephus.arrayOfJosephus(11, 3);
        int alive = 3;
        System.out.println("约琴夫排列：");
        for (int i = 0; i < 11; i++) {
            System.out.print(man[i] + " ");
            if (i == 10) {
                System.out.println();
            }
        }
    }
}
