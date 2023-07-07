package com.ot.tools.callback;

public class Teacher implements Callback {

    public void askQuestion(Student student) {
        new Thread(() -> {
            student.resolveQuestion(this);
        }).start();
    }

    @Override
    public void tellAnswer(int answer) {
        System.out.println("知道了，你的答案是：" + answer);
    }
}
