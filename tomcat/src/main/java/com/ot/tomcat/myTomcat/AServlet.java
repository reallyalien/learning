package com.ot.tomcat.myTomcat;

import java.io.IOException;

public class AServlet extends MyServlet{
    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("A");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) {
        super.doPost(myRequest, myResponse);
    }
}
