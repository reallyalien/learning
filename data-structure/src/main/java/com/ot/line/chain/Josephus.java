package com.ot.line.chain;

public class Josephus {
    //4 10 1 7 5 2 11 9 3 6 8
    public static int[] arrayOfJosephus(int number, int per) {
        int[] man = new int[number];
        //pos是当前元素的位置，count是要插入的数，一直在找空插入，每隔3下插入一次,3下当中如果其中已经有元素，则跳过，不算次数
        for (int count = 1, i = 0, pos = -1; count <= number; count++) {
            do {
                pos = (pos + 1) % number;  // 环状处理
                if (man[pos] == 0) {
                    i++;
                }
                if (i == per) {  // 报数为3了
                    i = 0;
                    //结束内层循环
                    break;
                }
            } while (true);
            man[pos] = count;
            System.out.println("死亡编号：" + pos);
        }
        return man;
    }

    public static void main(String[] args) {

        int[] man = Josephus.arrayOfJosephus(41, 3);
        System.out.println();
        for (int i = 0; i < man.length; i++) {
            if (man[i] == 40 || man[i] == 41) {
                System.out.println(i + 1);
            }
        }
        for (int i = 0; i < man.length; i++) {
            System.out.print(man[i]+"  ");
        }
        System.out.println();
    }
}
