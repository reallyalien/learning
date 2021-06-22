package com.ot.session.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * spring 的配置文件，contextLoaderListener需要加载的IOC容器 ,作为servlet所在IOC容器的父IOC容器
 */
@Configuration
@ComponentScan(
        basePackages = "com.ot.session",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)}
)
public class ApplicationConfig {
}
