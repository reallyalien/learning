package com.ot.spring.model;

import com.ot.spring.model.no.ApplyRequest;
import com.ot.spring.model.no.Manager;
import org.junit.Test;

public class TestDemo {

    @Test
    public void test1() {
        Manager manager1 = new Manager("jack1");
        Manager manager2 = new Manager("jack2");
        Manager manager3 = new Manager("jack3");

        ApplyRequest request = new ApplyRequest();
        request.setRequestType("加薪");
        request.setAppNumber(1000);
        request.setRequestContent("我要加薪");
        manager1.requestHandler(request, "经理");
        manager2.requestHandler(request, "总监");
        manager3.requestHandler(request, "总经理");
    }

    @Test
    public void test2(){

    }
}
