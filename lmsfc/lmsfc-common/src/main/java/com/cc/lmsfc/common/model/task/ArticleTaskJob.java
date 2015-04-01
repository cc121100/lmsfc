package com.cc.lmsfc.common.model.task;

import com.cc.lmsfc.common.model.article.ArticleElement;
import com.cc.lmsfc.common.model.filter.FilterRule;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-3-13.
 */

@Entity
@Table(name = "tbl_article_tkj")
public class ArticleTaskJob extends TaskJob {

    @Column(name = "url", nullable = false)
    @NotEmpty
    @Size(min = 1, max = 200)
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "filter_rule_id")
    private FilterRule filterRule;

//    @OneToOne
//    @JoinColumn(name = "article_ele_id")
//    private ArticleElement articleElement;

    @OneToOne(mappedBy = "articleTaskJob",fetch = FetchType.LAZY)
    private ArticleElement articleElement;

    //no need to load batchAtlTkj earge
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batch_art_tkj_id")
    private BatchArticleTaskJob batchArticleTaskJob;

    @Column(name = "is_whole", nullable = false)
    private boolean isWhole = false;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "tkj_run_log_id")
    private TaskJobRunLog taskJobRunLog;

    @Transient
    private Map<String,Object> tempMap = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FilterRule getFilterRule() {
        return filterRule;
    }

    public void setFilterRule(FilterRule filterRule) {
        this.filterRule = filterRule;
    }

    public ArticleElement getArticleElement() {
        return articleElement;
    }

    public void setArticleElement(ArticleElement articleElement) {
        this.articleElement = articleElement;
    }

    public BatchArticleTaskJob getBatchArticleTaskJob() {
        return batchArticleTaskJob;
    }

    public void setBatchArticleTaskJob(BatchArticleTaskJob batchArticleTaskJob) {
        this.batchArticleTaskJob = batchArticleTaskJob;
    }

    public Map<String, Object> getTempMap() {
        return tempMap;
    }

    public void setTempMap(Map<String, Object> tempMap) {
        this.tempMap = tempMap;
    }

    public boolean getIsWhole() {
        return isWhole;
    }

    public void setIsWhole(boolean isWhole) {
        this.isWhole = isWhole;
    }

    public boolean isWhole() {
        return isWhole;
    }

    public void setWhole(boolean isWhole) {
        this.isWhole = isWhole;
    }

    public TaskJobRunLog getTaskJobRunLog() {
        return taskJobRunLog;
    }

    public void setTaskJobRunLog(TaskJobRunLog taskJobRunLog) {
        this.taskJobRunLog = taskJobRunLog;
    }

    @Override
    public String[] getProperties() {
        return new String[]{"name","state","type","finishTime","url","filterRule","isWhole"};
    }
}
