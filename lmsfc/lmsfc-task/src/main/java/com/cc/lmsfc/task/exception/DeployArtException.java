package com.cc.lmsfc.task.exception;

import com.cc.lmsfc.common.model.task.ArticleTaskJob;

/**
 * Created by tomchen on 15-4-1.
 */
public class DeployArtException extends ArtTaskJobException {
    public DeployArtException() {
    }

    public DeployArtException(String message, ArticleTaskJob taskJob) {
        super(message, taskJob);
    }

    public DeployArtException(String message, Throwable cause, ArticleTaskJob taskJob) {
        super(message, cause, taskJob);
    }

    public DeployArtException(Throwable cause, ArticleTaskJob taskJob) {
        super(cause, taskJob);
    }

    public DeployArtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ArticleTaskJob taskJob) {
        super(message, cause, enableSuppression, writableStackTrace, taskJob);
    }
}
