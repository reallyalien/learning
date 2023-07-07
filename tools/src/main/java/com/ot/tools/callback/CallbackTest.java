package com.ot.tools.callback;

public class CallbackTest {

    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        Student rick = new Rick();
        teacher.askQuestion(rick);
        System.out.println("老师去干其他事情去了");
    }
}
