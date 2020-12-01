package com.ot.spring.demo;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class SpringContextLoaderListener extends ContextLoaderListener {
    @Override
    protected void customizeContext(javax.servlet.ServletContext sc, ConfigurableWebApplicationContext wac) {
        super.customizeContext(sc, wac);
        XmlWebApplicationContext xmlWebApplicationContext = (XmlWebApplicationContext) wac;
        //在这里将XmlWebApplicationContext属性allowBeanDefinitionOverriding设置为false,这个属性的值最终
        //会传递给DefaultListableBeanFactory类的allowBeanDefinitionOverriding属性
        xmlWebApplicationContext.setAllowBeanDefinitionOverriding(false);

    }
    /**
     * 需要在web.xml当中使用
     *
     *
     <listener>
     <listener-class>com.zyr.web.spring.SpringContextLoaderListener</listener-class>
     </listener>
     */
}
