package com.ot.spring.config;

import com.ot.spring.beans.Cat;
import com.ot.spring.beans.Chinese;
import com.ot.spring.beans.English;
import com.ot.spring.beans.Language;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.ot.spring.controller")
public class ConfigurationBean {

    @Bean
    @Lazy//这里有个顺序问题，加lazy注解之后，在spring启动之后才回去加载这个bean对象
    public Cat getCat() {
        System.out.println("延迟加载");
        return new Cat();
    }

    @Bean
    @Conditional(ChineseCondition.class)
    public Chinese getZh() {
        return new Chinese();
    }

    @Bean
    @Conditional(EnglishCondition.class)
    public English getEn() {
        return new English();
    }
}
