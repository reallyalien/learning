package com.ot.spring.proxy.dynamicproxy.cglib;

import com.ot.spring.proxy.dynamicproxy.impl.Me;
import org.junit.Test;

public class CglibTest {

    @Test
    public void cglib() {
        Me obj = (Me) new CglibProxy().getInstance(new Me());
        System.out.println(obj.getClass());//com.ot.spring.proxy.dynamicproxy.impl.Me$$EnhancerByCGLIB$$20fa90c5
        obj.findWork();
    }
}
