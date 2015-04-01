package com.cc.lmsfc.common.dao;

import com.cc.lmsfc.common.jpa.BaseRepository;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by tomchen on 15-3-24.
 */
public interface UserDAO extends BaseRepository<User, String> {

    @Query(value = "select u from User u where u.userName = :name")
    List<User> findByUserName(@Param("name") String userName);
}
