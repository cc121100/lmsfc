package com.cc.lmsfc.task.exception;

import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.common.model.task.TaskJob;

/**
 * Created by tomchen on 15-3-16.
 */
public class ArtTaskJobException extends RuntimeException {

    private ArticleTaskJob taskJob;

    public ArtTaskJobException() {
        super();
    }

    public ArtTaskJobException(String message, ArticleTaskJob taskJob) {
        super(message);
        this.taskJob = taskJob;
    }

    public ArtTaskJobException(String message, Throwable cause, ArticleTaskJob taskJob) {
        super(message, cause);
        this.taskJob = taskJob;
    }

    public ArtTaskJobException(Throwable cause, ArticleTaskJob taskJob) {
        super(cause);
        this.taskJob = taskJob;
    }

    protected ArtTaskJobException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ArticleTaskJob taskJob) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.taskJob = taskJob;
    }

    public ArticleTaskJob getTaskJob() {
        return taskJob;
    }

    public void setTaskJob(ArticleTaskJob taskJob) {
        this.taskJob = taskJob;
    }
}
