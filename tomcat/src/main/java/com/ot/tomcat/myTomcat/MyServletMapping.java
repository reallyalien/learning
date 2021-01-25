package com.ot.tomcat.myTomcat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyServletMapping {
    private String servletName;
    private String url;
    private String clazz;
}
