package com.ot.spring.factory;

public class FactoryBeanServiceImpl implements FactoryBeanService {

    @Override
    public void testFactoryBean() {
        System.out.println("我是FactoryBean的测试类");
    }
}
