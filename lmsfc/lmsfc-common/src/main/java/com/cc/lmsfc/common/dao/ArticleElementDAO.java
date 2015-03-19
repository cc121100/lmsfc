package com.cc.lmsfc.common.dao;

import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.article.ArticleElement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by tomchen on 15-3-18.
 */
public interface ArticleElementDAO extends BaseRepository<ArticleElement,String> {


    @Query(value = "select a from ArticleElement a where a.articleTaskJob.id = :id")
    List<ArticleElement> findByArticleTaskJobId(@Param("id") String id);
}
