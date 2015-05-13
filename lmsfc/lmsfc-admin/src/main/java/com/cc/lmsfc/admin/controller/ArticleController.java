package com.cc.lmsfc.admin.controller;

import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomchen on 15-3-23.
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController extends BaseCRUDController<Article,ArticleService,String> {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/index")
    public String toArticle(){
        return "article/index";
    }

    @Override
    protected ArticleService getService() {
        return articleService;
    }

    @Override
    protected String getPrefix() {
        return "article";
    }

    @InitBinder("article")
    public void initBinder1(WebDataBinder binder) {
        binder.setFieldDefaultPrefix(modelNameLower + ".");
    }
}
