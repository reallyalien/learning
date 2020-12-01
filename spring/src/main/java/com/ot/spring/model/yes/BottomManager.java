package com.ot.spring.model.yes;

import com.ot.spring.model.no.ApplyRequest;

/**
 * 经理
 */
public class BottomManager extends Manager {

    @Override
    public void requestHandler(ApplyRequest request) {
        if ("请假".equals(request.getRequestType()) && request.getAppNumber() <= 2) {
            System.out.println("请假数量小于等于2天，经理批准");
        } else {
            //自己无法处理，抛给上层领导
            if (null != superManager) superManager.requestHandler(request);
        }
    }
}
