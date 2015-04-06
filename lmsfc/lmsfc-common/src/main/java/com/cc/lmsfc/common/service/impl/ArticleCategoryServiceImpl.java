package com.cc.lmsfc.common.service.impl;

import com.cc.lmsfc.common.dao.ArticleCategoryDAO;
import com.cc.lmsfc.common.model.article.ArticleCategory;
import com.cc.lmsfc.common.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tomchen on 15-4-5.
 */
@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategory,ArticleCategoryDAO,String>
                                        implements ArticleCategoryService{

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Override
    protected ArticleCategoryDAO getRepository() {
        return articleCategoryDAO;
    }


}
