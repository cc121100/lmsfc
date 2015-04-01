package com.cc.lmsfc.common.model.task;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tomchen on 15-3-13.
 */
@Entity
@Table(name = "tbl_batch_article_tkj")
public class BatchArticleTaskJob extends TaskJob {

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy="batchArticleTaskJob",fetch = FetchType.LAZY)
    private List<ArticleTaskJob> articleTaskJobs;

    public List<ArticleTaskJob> getArticleTaskJobs() {
        return articleTaskJobs;
    }

    public void setArticleTaskJobs(List<ArticleTaskJob> articleTaskJobs) {
        this.articleTaskJobs = articleTaskJobs;
    }

    @Override
    public String[] getProperties() {
        return new String[0];
    }
}
