package com.ot.concurrent.await;

import java.util.concurrent.Exchanger;

public class ExchangeDemo1 {

    //创建交换器
    private static final Exchanger<String> exchanger=new Exchanger<>();

    /**
     * 将当前线程id塞入交换器，同时获取交换器当中的线程id
     * @param
     */
    public void update(){
        new Thread(()->{
            try {
                String i = exchanger.exchange(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName()+"进行交换的线程："+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        ExchangeDemo1 demo1 = new ExchangeDemo1();
        for (int i = 0; i < 4; i++) {
            demo1.update();
        }
    }
}
