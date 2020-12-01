package com.ot.design.a_duty;

/**
 * 记录日志
 * log4j的8个日志级别由高到低（OFF、FATAL、ERROR、WARN、INFO、DEBUG、TRACE、 ALL）
 *
 * 如果将log level设置在某一个级别上，那么比此级别优先级高的log都能打印出来。
 * 例如，如果设置优先级为WARN，那么OFF、FATAL、ERROR、WARN 4个级别的log能正常输出，
 * 而INFO、DEBUG、TRACE、 ALL级别的log则会被忽略。Log4j建议只使用四个级别，优先级从高到低分别是ERROR、WARN、INFO、DEBUG。
 *
 * 从我们实验的结果可以看出，log4j默认的优先级为ERROR或者WARN（实际上是ERROR）。
 */
public abstract class AbstractLogger {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    protected int level;

    /**
     * 责任链当中的下一个元素
     */
    protected AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) {
        //如果当前的日志级别高于指定的级别才打印
        if (this.level >= level) {
            write(message);
        }else {
            if (nextLogger != null) {
                nextLogger.logMessage(level, message);
            }
        }
    }

    public abstract void write(String message);
}
