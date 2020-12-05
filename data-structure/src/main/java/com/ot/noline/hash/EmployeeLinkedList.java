package com.ot.noline.hash;

/**
 * 存放雇员的链表
 */
public class EmployeeLinkedList {

    private Employee head;//头指针，指向第一个雇员

    //添加雇员信息
    public void add(Employee employee) {
        //如果添加第一个
        if (head == null) {
            head = employee;
            return;
        }
        //如果不是添加第一个雇员，
        Employee cur = head;
        while (true) {
            if (cur.next == null) {
                break;
            }
            cur = cur.next;
        }
        cur.next = employee;
    }

    public void list() {
        if (head == null) {
            System.out.println("链表为空");
            return;
        }
        Employee cur = head;
        while (cur != null) {
            System.out.printf("id=%d,name=%s=>", cur.getId(), cur.getName());
            cur = cur.next;
        }
        System.out.println();
    }

    public Employee find(int id) {
        //判断当前链表是否为空
        if (head == null) {
            System.out.println("链表为空");
            return null;
        }
        Employee cur = head;
        while (cur != null) {
            if (cur.getId().equals(id)) {
                return cur;
            }
            cur = cur.next;
        }
        //while没有找到，返回null
        return null;
    }
}
