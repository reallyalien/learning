package com.ot.example.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class LogTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Logger logger = LoggerFactory.getLogger(LogTest.class);
        ClassLoader classLoader = logger.getClass().getClassLoader();
        Method getURLs = classLoader.getClass().getMethod("getURLs");
        getURLs.setAccessible(true);
        URL[] urls = (URL[]) getURLs.invoke(classLoader);
        for (URL url : urls) {
            System.out.println(url);
        }
        logger.info("log.......");
    }
}

/*
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".   这个类只在实现类当中存在，api当中没有，
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
 */