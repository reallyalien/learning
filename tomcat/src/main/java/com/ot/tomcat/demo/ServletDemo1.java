package com.ot.tomcat.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name ="/servlet1",urlPatterns = "/servlet1")
public class ServletDemo1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("servlet-->service");
    }

    @Override
    public void destroy() {
        System.out.println("servlet-->destroy");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("servlet-->init");
    }
}
