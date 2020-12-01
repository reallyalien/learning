package com.ot.line.chain;

/**
 * 约瑟夫问题
 */
public class Josepfu {

    public static void main(String[] args) {
        SingleCircleLinkedList list = new SingleCircleLinkedList();
        list.add(11);
        list.show();
        list.chu(1, 3, 11);
    }

    static class SingleCircleLinkedList {
        //辅助指针指向第一个小孩,
        private Boy first = null;

        //添加小孩节点，构建环形链表
        public void add(int nums) {
            //暂且不校验
            //创建环形链表
            Boy curBoy = null;//辅助指针
            for (int i = 1; i <= nums; i++) {
                Boy boy = new Boy(i);
                if (i == 1) {
                    first = boy;
                    first.next = first;
                    curBoy = first;
                } else {
                    curBoy.next = boy;
                    boy.next = first;
                    curBoy = boy;
                }
            }
        }

        public void show() {
            //判断链表是否为空
            if (first == null) {
                System.out.println("链表为空");
            }
            Boy cur = first;
            while (true) {
                System.out.printf("小孩的编号%d\n", cur.no);
                if (cur.next == first) {
                    System.out.println("遍历结束");
                    break;
                } else {
                    cur = cur.next;
                }
            }
        }

        /**
         * @param startNo  从第几个小孩开始数
         * @param countNum 数几下
         * @param nums     表示最初有多少小孩在圈中
         */
        public void chu(int startNo, int countNum, int nums) {
            if (first == null || startNo < 1 || startNo > nums) {
                System.out.println("参数输入有误，请重新输入");
            }
            //创建一个辅助指针
            Boy helper = first;
            while (true) {
                if (helper.next == first) {
                    //helper指向最后一个节点
                    break;
                }
                helper = helper.next;
            }
            //小孩报数从startNo开始,需要先移动指针到startNo这个位置
            for (int i = 0; i < startNo - 1; i++) {
                first = first.next;
                helper = helper.next;
            }
            //开始报数，让first和helper移动countNum-1次
            //循环操作
            while (true) {
                if (helper == first) {
                    //只剩下一个人，
                    break;
                }
                for (int i = 0; i < countNum - 1; i++) {
                    first = first.next;
                    helper = helper.next;
                }
                //first指向的就是要出圈的小孩
                System.out.printf("小孩%d出圈\n", first.no);
                //出圈之后，first先后移动，helper指向新的first，原来的first出去，新的一次循环开始，first自己先数一下,
                first = first.next;
                helper.next = first;
            }
            System.out.printf("最后留在圈中的小孩编号%d\n", first.no);
        }
    }

    static class Boy {
        private int no;
        private Boy next;

        public Boy() {
        }

        public Boy(int no) {
            this.no = no;
        }
    }
}
