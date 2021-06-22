package com.ot.http.jdk;

import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import java.net.HttpURLConnection;

public class JdkHttp {

    public static void main(String[] args) {
        HttpURLConnection connection=new HttpsURLConnectionImpl(null);
    }
}
