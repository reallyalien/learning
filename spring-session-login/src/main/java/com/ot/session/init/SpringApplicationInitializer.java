package com.ot.session.init;

import com.ot.session.config.ApplicationConfig;
import com.ot.session.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * SpringApplicationInitializer相当于web.xml，使用servlet3.0开发不需要在定义web.xml
 * spring容器启动时加载WebApplicationInitializer接口的所有实现类
 */
public class SpringApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    /**
     * 指定rootContext的配置类
     *
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationConfig.class};
    }

    /**
     * 指定servlet的配置类
     *
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
