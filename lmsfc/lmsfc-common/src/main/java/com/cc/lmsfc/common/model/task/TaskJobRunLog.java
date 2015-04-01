package com.cc.lmsfc.common.model.task;

import com.cc.lmsfc.common.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by tomchen on 15-4-1.
 */
@Entity
@Table(name = "tbl_tkj_run_log")
public class TaskJobRunLog extends BaseModel {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;

    @Column(name = "log_content",length = 10000,nullable = true)
    private String logContent;


    @OneToOne(mappedBy = "taskJobRunLog")
    private ArticleTaskJob articleTaskJob;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public ArticleTaskJob getArticleTaskJob() {
        return articleTaskJob;
    }

    public void setArticleTaskJob(ArticleTaskJob articleTaskJob) {
        this.articleTaskJob = articleTaskJob;
    }

    @Override
    public String[] getProperties() {
        return new String[]{"logContent"};
    }
}
