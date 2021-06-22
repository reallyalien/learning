package com.ot.concurrent.basic;

public class JoIn {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true){

            }
        });
        thread.start();
        thread.join();
        //在main线程调用thread的wait方法，会让当前线程阻塞，此时thread就相当于一个锁对象，
    }
}
