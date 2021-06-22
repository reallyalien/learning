package com.ot.tools.anno;


import cn.hutool.core.annotation.AnnotationUtil;
import org.springframework.context.annotation.AnnotationConfigUtils;

import java.lang.annotation.Annotation;

public class AnnoTest {

    public static void main(String[] args) {
        Class<Human> clazz = Human.class;
        Anno2 anno2 = clazz.getAnnotation(Anno2.class);
        Anno1 annotation = anno2.getClass().getAnnotation(Anno1.class);
        System.out.println();
    }
}
