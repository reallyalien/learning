package com.ot.tomcat.demo;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDemo {

    public static void main(String[] args) throws LifecycleException {
        //自定义一个servlet用来处理http请求
        HttpServlet httpServlet = new HttpServlet(){
            @Override
            protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                resp.getWriter().write("hello world");
            }
            @Override
            public void destroy() {
                System.out.println("servlet-->destroy");
            }

            @Override
            public void init() throws ServletException {
                System.out.println("servlet-->init");
            }
        };
        //引入嵌入式的tomcat
        Tomcat tomcat = new Tomcat();
        //部署应用的context
        Context context = tomcat.addContext("/demo", null);
        //添加servlet
        Tomcat.addServlet(context,"hello",httpServlet);
        //添加servletMapping
        context.addServletMappingDecoded("/hello","hello");
        //启动tomcat
        tomcat.init();
        tomcat.start();
        //阻塞tomcat，等待请求
        tomcat.getServer().await();
        //http://localhost:8080/demo/hello
    }
}
