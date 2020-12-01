package com.ot.design.a_duty;

public class ConsoleLogger extends AbstractLogger {
    public ConsoleLogger(int level) {
        this.level = level;
    }

    @Override
    public void write(String message) {
        System.out.println("consoleLogger::" + message  );
    }
}
