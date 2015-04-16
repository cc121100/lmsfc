package com.cc.lmsfc.common.service;

import com.cc.lmsfc.common.dao.ArticleTaskJobDAO;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;

/**
 * Created by tomchen on 15-3-30.
 */
public interface ArticleTaskJobService extends BaseService<ArticleTaskJob,ArticleTaskJobDAO,String> {

    public void updateState(String id,boolean isSuccess);

    public ArticleTaskJob saveAndReturn(ArticleTaskJob atj);

    public ArticleTaskJob getAtjAndLog(String id);
}
