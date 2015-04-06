package com.cc.lmsfc.task.exception;

import com.cc.lmsfc.common.model.task.ArticleTaskJob;

/**
 * Created by tomchen on 15-4-1.
 */
public class GenerateArtEleException extends ArtTaskJobException {
    public GenerateArtEleException() {
    }

    public GenerateArtEleException(String message, ArticleTaskJob taskJob) {
        super(message, taskJob);
    }

    public GenerateArtEleException(String message, Throwable cause, ArticleTaskJob taskJob) {
        super(message, cause, taskJob);
    }

    public GenerateArtEleException(Throwable cause, ArticleTaskJob taskJob) {
        super(cause, taskJob);
    }

    public GenerateArtEleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ArticleTaskJob taskJob) {
        super(message, cause, enableSuppression, writableStackTrace, taskJob);
    }
}
