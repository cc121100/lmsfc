package com.cc.lmsfc.task.exception;

import com.cc.lmsfc.common.model.task.TaskJob;

/**
 * Created by tomchen on 15-4-1.
 */
public class GetArticleException extends TaskJobException {

    public GetArticleException() {
    }

    public GetArticleException(String message, TaskJob taskJob) {
        super(message, taskJob);
    }

    public GetArticleException(String message, Throwable cause, TaskJob taskJob) {
        super(message, cause, taskJob);
    }

    public GetArticleException(Throwable cause, TaskJob taskJob) {
        super(cause, taskJob);
    }

    public GetArticleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, TaskJob taskJob) {
        super(message, cause, enableSuppression, writableStackTrace, taskJob);
    }
}
