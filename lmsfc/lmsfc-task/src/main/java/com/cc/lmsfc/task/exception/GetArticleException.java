package com.cc.lmsfc.task.exception;

import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.common.model.task.TaskJob;

/**
 * Created by tomchen on 15-4-1.
 */
public class GetArticleException extends ArtTaskJobException {

    public GetArticleException() {
    }

    public GetArticleException(String message, ArticleTaskJob taskJob) {
        super(message, taskJob);
    }

    public GetArticleException(String message, Throwable cause, ArticleTaskJob taskJob) {
        super(message, cause, taskJob);
    }

    public GetArticleException(Throwable cause, ArticleTaskJob taskJob) {
        super(cause, taskJob);
    }

    public GetArticleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ArticleTaskJob taskJob) {
        super(message, cause, enableSuppression, writableStackTrace, taskJob);
    }
}
