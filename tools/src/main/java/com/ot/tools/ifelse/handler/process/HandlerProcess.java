package com.ot.tools.ifelse.handler.process;

import com.ot.tools.ifelse.anno.HandlerType;
import com.ot.tools.ifelse.enums.HandlerTypeEnum;
import com.ot.tools.ifelse.handler.context.HandlerContext;
import com.ot.tools.ifelse.utils.ClassScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class HandlerProcess implements BeanFactoryPostProcessor {

    private static final String PACKAGE = "com.ot.tools.ifelse.handler";

    /**
     * 扫描指定包，初始化handlerContext，将其注入到spring容器
     *
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        HashMap<String, Class> map = new HashMap<>();
        ClassScanner.scan(PACKAGE, HandlerType.class).forEach(clazz ->{
            HandlerTypeEnum value = clazz.getAnnotation(HandlerType.class).value();
            String type = value.getType();
            map.put(type,clazz);
        } );
        //初始化handlerContext，注入spring容器
        HandlerContext handlerContext = new HandlerContext(map);
        configurableListableBeanFactory.registerSingleton("handlerContext", handlerContext);
    }
}
