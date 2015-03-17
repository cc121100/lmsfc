package com.cc.lmsfc.common.model.task;

import com.cc.lmsfc.common.model.article.ArticleElement;
import com.cc.lmsfc.common.model.filter.FilterRule;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
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
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_rule_id")
    private FilterRule filterRule;

//    @OneToOne
//    @JoinColumn(name = "article_ele_id")
//    private ArticleElement articleElement;

    @OneToOne(mappedBy = "articleTaskJob",fetch = FetchType.LAZY)
    private ArticleElement articleElement;

    //no need to load batchAtlTkj earge
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_art_tkj_id")
    private BatchArticleTaskJob batchArticleTaskJob;

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
}
