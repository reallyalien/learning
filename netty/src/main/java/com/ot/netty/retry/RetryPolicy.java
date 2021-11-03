package com.ot.netty.retry;

public interface RetryPolicy {

    boolean allowRetry(int retryCount);

    long getSleepTimeMs(int retryCount);
}
