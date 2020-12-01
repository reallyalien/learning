package com.ot.spring.model.yes;

import com.ot.spring.model.no.ApplyRequest;

public class TopManager extends Manager {

    @Override
    public void requestHandler(ApplyRequest request) {
        if ("请假".equals(request.getRequestType())) {
            System.out.println("总经理批准");
        } else if ("加薪".equals(request.getRequestType())) {
            if (request.getAppNumber() <= 300) {
                System.out.println("总经理同意加薪");
            } else {
                System.out.println("总经理不同意加薪");
            }
        }
    }
}
