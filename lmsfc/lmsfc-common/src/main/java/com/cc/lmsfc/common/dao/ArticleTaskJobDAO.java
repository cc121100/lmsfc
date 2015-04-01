package com.cc.lmsfc.common.dao;

import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by tomchen on 15-3-17.
 */
public interface ArticleTaskJobDAO extends BaseRepository<ArticleTaskJob,String> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update ArticleTaskJob atj set atj.state = :state where atj.id = :id")
    void updateArticleState(@Param("id")String id, @Param("state")int state);
}
