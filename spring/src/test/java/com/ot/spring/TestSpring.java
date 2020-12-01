package com.ot.spring;

import com.ot.spring.beans.Cat;
import com.ot.spring.beans.Language;
import com.ot.spring.config.ConfigurationBean;
import com.ot.spring.controller.HelloController;
import com.ot.spring.factory.FactoryBeanService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class TestSpring {

    ClassPathXmlApplicationContext applicationContext;

    @Test
    public void method1() {
        applicationContext = new ClassPathXmlApplicationContext("application.xml");
        applicationContext.start();
        System.out.println("spring容器初始化完成");
        Cat cat = applicationContext.getBean("cat", Cat.class);
        System.out.println(cat);
    }

    @Test
    public void method2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationBean.class);
        context.start();
        System.out.println("spring容器初始化完成");
        Map<String, Cat> beans = context.getBeansOfType(Cat.class);
        System.out.println(beans);
    }

    @Test
    public void method3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationBean.class);
        context.start();
        Map<String, Language> beans = context.getBeansOfType(Language.class);
        System.out.println(beans);
    }

    @Test
    public void method4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationBean.class);
        context.start();
        String[] definitionNames = context.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void method5() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationBean.class);
        context.start();
        HelloController helloController1 = context.getBean("helloController", HelloController.class);
        helloController1.doRequest();
        HelloController helloController2 = context.getBean("helloController", HelloController.class);
        helloController2.doRequest();
    }

    @Test
    public void factoryBeanTest(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        FactoryBeanService factoryBeanService = applicationContext.getBean(FactoryBeanService.class);
        factoryBeanService.testFactoryBean();
    }

}
