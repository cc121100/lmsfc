package com.cc.lmsfc.task.exception;

/**
 * Created by tomchen on 15-3-16.
 */
public class TaskJobException extends RuntimeException {
    public TaskJobException() {
        super();
    }

    public TaskJobException(String message) {
        super(message);
    }

    public TaskJobException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskJobException(Throwable cause) {
        super(cause);
    }

    protected TaskJobException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
