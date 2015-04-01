package com.cc.lmsfc.task.exception;

import com.cc.lmsfc.common.model.task.TaskJob;

/**
 * Created by tomchen on 15-3-16.
 */
public class TaskJobException extends RuntimeException {

    private TaskJob taskJob;

    public TaskJobException() {
        super();
    }

    public TaskJobException(String message,TaskJob  taskJob) {
        super(message);
        this.taskJob = taskJob;
    }

    public TaskJobException(String message, Throwable cause,TaskJob taskJob) {
        super(message, cause);
        this.taskJob = taskJob;
    }

    public TaskJobException(Throwable cause, TaskJob taskJob) {
        super(cause);
        this.taskJob = taskJob;
    }

    protected TaskJobException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,TaskJob taskJob) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.taskJob = taskJob;
    }

    public TaskJob getTaskJob() {
        return taskJob;
    }

    public void setTaskJob(TaskJob taskJob) {
        this.taskJob = taskJob;
    }
}
