package com.ot.spring.beans;

import com.ot.spring.config.ConfigurationBean;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class SpringTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationBean.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }


    /**
     * 编程式加载xml配置文件
     */
    @Test
    public void test1(){
        //资源信息
        ClassPathResource resource = new ClassPathResource("");
        //建立beanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //创建xml的读取器
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //加载配置文件
        reader.loadBeanDefinitions(resource);
    }
}
