package com.cc.lmsfc.common.service.impl;

import com.cc.lmsfc.common.dao.ArticleDAO;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tomchen on 15-4-22.
 */
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article,ArticleDAO,String> implements ArticleService {

    @Autowired
    private ArticleDAO articleDAO;

    @Override
    protected ArticleDAO getRepository() {
        return articleDAO;
    }


}
