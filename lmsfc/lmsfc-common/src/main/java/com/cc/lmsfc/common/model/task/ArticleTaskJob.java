package com.cc.lmsfc.common.model.task;

import com.cc.lmsfc.common.model.article.ArticleElement;
import com.cc.lmsfc.common.model.filter.FilterRule;
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

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,mappedBy = "articleTaskJob" )
    private List<ArtTaskJobRunLog> taskJobRunLogs;

    @Column(name = "target_catgory",nullable = true)
    @Size(max = 100)
    private String targetCategory;

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

    public List<ArtTaskJobRunLog> getTaskJobRunLogs() {
        return taskJobRunLogs;
    }

    public void setTaskJobRunLogs(List<ArtTaskJobRunLog> taskJobRunLogs) {
        this.taskJobRunLogs = taskJobRunLogs;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    @Override
    public String[] getProperties() {
        return new String[]{"name","state","type","finishTime","url","filterRule","isWhole","targetCategory"};
    }
}
