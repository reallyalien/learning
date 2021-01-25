package com.ot.noline.hash;


/**
 * 管理多条链表
 */
public class HashTab {
    private EmployeeLinkedList[] tables;

    private int size;

    public HashTab(int size) {
        this.size = size;
        tables = new EmployeeLinkedList[size];
        //初始化链表
        EmployeeLinkedList list = null;
        for (int i = 0; i < size; i++) {
            list = new EmployeeLinkedList();
            tables[i] = list;
        }
    }

    public void add(Employee employee) {
        //根据员工的id应该添加到那一条链表
        int hash = hash(employee.getId());
        //添加到对应的链表当中
        tables[hash].add(employee);
    }

    /**
     * 查找
     * @param id
     * @return
     */
    public Employee find(int id) {
        int hash = hash(id);
        return tables[hash].find(id);
    }

    /**
     * 遍历哈希表
     */
    public void list() {
        for (int i = 0; i < size; i++) {
            System.out.printf("table[%d]\t", i);
            tables[i].list();
        }
    }

    /**
     * 哈希函数
     *
     * @param id
     * @return
     */
    public int hash(int id) {
        return id % size;
    }


    public static void main(String[] args) {
        HashTab hashTab = new HashTab(7);
        hashTab.add(new Employee(1, "小明"));
        hashTab.add(new Employee(2, "小红"));
        hashTab.add(new Employee(8, "小红kk"));
        Employee employee = hashTab.find(1);
        System.out.println(employee);
    }
}
