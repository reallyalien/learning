package com.ot.tools.ifelse.anno;

import com.ot.tools.ifelse.enums.HandlerTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HandlerType {
    HandlerTypeEnum value();
}
