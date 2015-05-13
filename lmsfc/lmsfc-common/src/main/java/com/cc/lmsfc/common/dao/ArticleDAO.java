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

    @Query(value = "select a from Article a where a.articleCategory.name = :name order by a.createdDt desc")
    List<Article> findByCategory(@Param("name") String name);

    @Query(value = "select a from Article a where a.state = :state order by a.generateTime desc")
    List<Article> findByState(@Param("state") int state);
}
