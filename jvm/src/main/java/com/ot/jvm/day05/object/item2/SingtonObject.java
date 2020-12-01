package com.ot.jvm.day05.object.item2;

import java.util.stream.IntStream;

public class SingtonObject {

    private SingtonObject(){

    }
    //===========================================================================
    private enum Sington{
        INSTANCE;
        private final SingtonObject instance;
        Sington(){
            instance=new SingtonObject();
        }
        public SingtonObject getInstance(){
            return instance;
        }
    }
    //=========================================================================
    public static SingtonObject getInstance(){
        return Sington.INSTANCE.getInstance();
    }

    public static void main(String[] args) {
        IntStream.rangeClosed(1,100).forEach(i->new Thread(String.valueOf(i)){
            @Override
            public void run() {
                System.out.println(SingtonObject.getInstance());
            }
        }.start());
    }

}
