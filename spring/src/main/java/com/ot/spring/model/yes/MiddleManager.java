package com.ot.spring.model.yes;

import com.ot.spring.model.no.ApplyRequest;

public class MiddleManager extends Manager {
    @Override
    public void requestHandler(ApplyRequest request) {
        if ("请假".equals(request.getRequestType()) && request.getAppNumber() <= 5) {
            System.out.println("请假数量小于等于5天，总监批准");
        } else {
            if (null != superManager) superManager.requestHandler(request);
        }
    }
}
