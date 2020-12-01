package com.ot.nio.time;

public class TimeServer {

    public static void main(String[] args) {
        int port=8080;
        MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(port);
        new Thread(multiplexerTimeServer).start();
    }
}
