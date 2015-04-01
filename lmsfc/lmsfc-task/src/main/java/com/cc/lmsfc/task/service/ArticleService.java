package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.dao.ArticleCategoryDAO;
import com.cc.lmsfc.common.dao.ArticleDAO;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.article.ArticleCategory;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.task.constant.TaskConstants;
import freemarker.core.Configurable;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-3-19.
 */
@Component
public class ArticleService {

    @Autowired
    private Configuration configuration;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Transactional
    public void generate(ArticleTaskJob atj){

        // 1 article page
        try {
            //todo articleCategoryDAO.findAll();
            List<ArticleCategory> articleCategorys = articleCategoryDAO.findAll();


            ArticleCategory articleCategory = articleCategorys.get(0);
            String artFileName = atj.getArticleElement().getId() + ".html";

            Article article = new Article();
            article.setName((String) atj.getTempMap().get("title"));
            article.setDescription(article.getName());
            article.setGenerateTime(new Date());
            article.setState(0);
            article.setArtFileName(artFileName);
            article.setArticleCategory(articleCategory);
            article.setArticleElement(atj.getArticleElement());
            articleDAO.saveAndFlush(article);

            Map<String, Object> map = new HashMap<>();
            map.put("title",atj.getTempMap().get("title"));
            map.put("content",atj.getTempMap().get("content"));
            map.put("innnercss",atj.getTempMap().get("innnercss"));
            map.put("outercss",atj.getTempMap().get("outercss"));
            map.put("postDate",article.getGenerateTime());
            map.put("from",atj.getUrl());
            map.put("type","article");
            map.put("artCateList",articleCategorys);


            String cateGeneratedFloder = TaskConstants.GENERATED_FLODER + CommonConsts.SLASH + article.getArticleCategory().getPathName();
            File f = new File(cateGeneratedFloder);
            if(!f.exists()){
                f.mkdirs();
            }

            String destFileName = cateGeneratedFloder + CommonConsts.SLASH  + artFileName;


            Template template = configuration.getTemplate("template.ftl");
            generateFile(template,map,destFileName);


            // 2 category list page
            map.clear();
            map.put("type","list");

            //get all articles of this category

            List<Article> articleList = articleDAO.findByCategory(articleCategory.getId());
            map.put("articleList",articleList);
            map.put("artCateList",articleCategorys);

            destFileName = cateGeneratedFloder + CommonConsts.SLASH +  "list.html";

            generateFile(template,map,destFileName);

        } catch (IOException e) {
            //todo handler exception
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

//        return atj;
    }

    public void generateFile(Template template, Map<String,Object> paramMap,String destFileStr) throws IOException, TemplateException {
        File destFile = new File(destFileStr);
//        if(!destFile.exists()){
//            destFile.createNewFile();
//        }
        Writer out = new OutputStreamWriter(new FileOutputStream(destFile),"UTF-8");
        template.process(paramMap,out);
        out.flush();

    }

    public void deploy(){

    }

    public void copyArticlePage(){

    }

    public void copyListPage(){

    }

}
