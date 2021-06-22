package com.ot.jvm.day04.clazzload;

import java.util.ServiceLoader;

public class ServiceLoaderTest {

    public static void main(String[] args) {
        ServiceLoader<Object> serviceLoader =ServiceLoader.load(Object.class);
    }
}
