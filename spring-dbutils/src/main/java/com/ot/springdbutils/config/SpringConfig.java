package com.ot.springdbutils.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.ot.springdbutils")
@Import(DruidConfig.class)
public class SpringConfig {



}
