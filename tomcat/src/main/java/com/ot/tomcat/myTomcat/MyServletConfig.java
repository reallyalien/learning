package com.ot.tomcat.myTomcat;

import java.util.ArrayList;
import java.util.List;

public class MyServletConfig {

    public static List<MyServletMapping> servletMappingList = new ArrayList<>();

    static {
        servletMappingList.add(new MyServletMapping("A", "/A", "com.ot.tomcat.myTomcat.AServlet"));
        servletMappingList.add(new MyServletMapping("B", "/B", "com.ot.tomcat.myTomcat.BServlet"));
    }
}
