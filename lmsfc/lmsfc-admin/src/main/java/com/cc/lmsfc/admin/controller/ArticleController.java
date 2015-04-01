package com.cc.lmsfc.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomchen on 15-3-23.
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController {

    @RequestMapping(value = "/index")
    public String toArticle(){
        return "article/index";
    }
}
