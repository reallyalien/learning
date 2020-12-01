package com.ot.tomcat.myTomcat;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyTomcat {

    private int port;
    private Map<String, String> urlServletMapping = new HashMap<>(); //存储url 和对应的类

    public MyTomcat(Integer port) {
        this.port = port;
    }

    public void start() {
        initServletMapping();
        try {
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("客户端已建立连接");
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                MyRequest request = new MyRequest(inputStream);
                MyResponse response = new MyResponse(outputStream);
                dispatch(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //初始化映射
    public void initServletMapping() {
        for (MyServletMapping servletMapping : MyServletConfig.servletMappingList) {
            urlServletMapping.put(servletMapping.getUrl(), servletMapping.getClazz());
        }
    }

    public void dispatch(MyRequest request, MyResponse response) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String clazz = urlServletMapping.get(request.getUrl());
        Class<MyServlet> myServletClass = (Class<MyServlet>) Class.forName(clazz);
        MyServlet myServlet = myServletClass.newInstance();
        myServlet.service(request, response);
    }

    public static void main(String[] args) {
        MyTomcat myTomcat = new MyTomcat(8080);
        myTomcat.start();
    }
}
