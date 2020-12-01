package com.ot.socket;

import org.junit.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class INetAddressApi {

    @Test
    public void test1(){
        //连接到DNS服务器来解析主机名
        InetAddress inetAddress=null;
        try {
            inetAddress=InetAddress.getByName("www.jd.com");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(inetAddress);
    }
    @Test
    public void test2(){
        //连接到DNS服务器来解析主机名
        InetAddress inetAddress=null;
        try {
            inetAddress=InetAddress.getByName("14.215.177.38");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(inetAddress);
    }
    @Test
    public void test3(){
        //连接到DNS服务器来解析主机名
        InetAddress[] inetAddress=null;
        try {
            inetAddress=InetAddress.getAllByName("www.jd.com");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(inetAddress));
    }
    @Test
    public void test4(){
        //连接到DNS服务器来解析主机名
        InetAddress inetAddress=null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(inetAddress);
    }
}
