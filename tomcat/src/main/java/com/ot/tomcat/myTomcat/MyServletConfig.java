package com.ot.tomcat.myTomcat;

import java.util.ArrayList;
import java.util.List;

public class MyServletConfig {

    public static List<MyServletMapping> servletMappingList = new ArrayList<>();

    static {
        servletMappingList.add(new MyServletMapping(
                "index",
                "/index",
                "com.ot.tomcat.myTomcat.IndexServlet"
        ));
    }
}
