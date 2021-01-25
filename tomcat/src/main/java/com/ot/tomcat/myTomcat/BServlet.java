package com.ot.tomcat.myTomcat;

import java.io.IOException;

public class BServlet extends MyServlet{

    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("B");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
