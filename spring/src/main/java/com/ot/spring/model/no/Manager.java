package com.ot.spring.model.no;

public class Manager {

    private String name;

    public Manager(String name) {
        this.name = name;
    }

    public void requestHandler(ApplyRequest request, String level) {
        if ("经理".equals(level)) {
            if ("请假".equals(request.getRequestType()) && request.getAppNumber() <= 2) {
                System.out.println("请假数量小于等于2天，经理：" + this.name + "批准");
            } else {
                System.out.println("经理" + this.name + "无权审批");
            }
        } else if ("总监".equals(level)) {
            if ("请假".equals(request.getRequestType()) && request.getAppNumber() <= 5) {
                System.out.println("请假数量小于等于5天，总监：" + this.name + "批准");
            } else {
                System.out.println("经理" + this.name + "无权审批");
            }
        } else if ("总经理".equals(level)) {
            if ("请假".equals(request.getRequestType())) {
                System.out.println("总监：" + this.name + "批准");
            } else if ("加薪".equals(request.getRequestType())) {
                if (request.getAppNumber() <= 300) {
                    System.out.println("总经理" + this.name + "同意加薪");
                } else {
                    System.out.println("总经理" + this.name + "不同意加薪");
                }
            }
        }
    }
}
