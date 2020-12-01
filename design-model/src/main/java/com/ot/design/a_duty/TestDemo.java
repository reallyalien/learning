package com.ot.design.a_duty;

public class TestDemo {

    public static AbstractLogger getChainOfLogger() {
        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);
        consoleLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(errorLogger);
        return errorLogger;
    }

    public static void main(String[] args) {
        AbstractLogger logger = getChainOfLogger();
        logger.logMessage(AbstractLogger.INFO,
                "this is  info information");
        logger.logMessage(AbstractLogger.DEBUG,
                "This is  debug level information.");
        logger.logMessage(AbstractLogger.ERROR,
                "This is  error information.");
    }
}
