package com.cc.lmsfc.common.dao;

import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.article.ArticleCategory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tomchen on 15-3-20.
 */
public interface ArticleCategoryDAO extends BaseRepository<ArticleCategory, String> {

    @Query(value = "select a from ArticleCategory a where a.status = 'A' order by a.sequence")
    List<ArticleCategory> findActiveCatOrderBySequence();

}
