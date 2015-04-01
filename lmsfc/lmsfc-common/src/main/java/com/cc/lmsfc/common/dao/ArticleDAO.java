package com.cc.lmsfc.common.dao;

import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.article.ArticleElement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by tomchen on 15-3-20.
 */
public interface ArticleDAO  extends BaseRepository<Article, String> {

    @Query(value = "select a from Article a where a.articleCategory.id = :id")
    List<Article> findByCategory(@Param("id") String cateId);
}
