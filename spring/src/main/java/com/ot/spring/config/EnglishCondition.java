package com.ot.spring.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.IOException;
import java.util.Properties;

/**
 * 英文条件类
 */
public class EnglishCondition implements Condition {

    /**
     * @param conditionContext      spring上下文
     * @param annotatedTypeMetadata 注解元数据
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Properties properties = new Properties();
        try {
            properties.load(conditionContext.getResourceLoader().getResource("language.properties").getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "english".equals(properties.getProperty("key"));
    }
}
