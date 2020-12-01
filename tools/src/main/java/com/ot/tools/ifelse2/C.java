package com.ot.tools.ifelse2;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/c")
public class C implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @GetMapping("/h")
    public Object h(){
        Map<String, Class> classMap = new HashMap<>();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(D.class);
        Collection<Object> values = beansWithAnnotation.values();
        Class c=null;
        for (Object value : values) {
            String value1 = value.getClass().getAnnotation(D.class).value();
           classMap.put(value1,value.getClass());
           c=value.getClass();
        }
        Object bean = applicationContext.getBean(c);
        return bean;
    }

}
