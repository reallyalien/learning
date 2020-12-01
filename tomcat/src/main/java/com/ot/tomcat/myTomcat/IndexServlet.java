package com.ot.tomcat.myTomcat;

import java.io.IOException;

public class IndexServlet extends MyServlet{

    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("hello tomcat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("hello tomcat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
