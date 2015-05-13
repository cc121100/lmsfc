package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.dao.ArticleCategoryDAO;
import com.cc.lmsfc.common.dao.ArticleDAO;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.article.ArticleCategory;
import com.cc.lmsfc.task.constant.TaskConstants;
import com.cc.lmsfc.task.helper.FreemarkerHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-4-23.
 */
@Service
public class ReAssembleService {

    @Autowired
    private FreemarkerHelper freemarkerHelper;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    public void reassemble(Message<?>  msg){

        List<String> successList = Lists.newArrayList();
        Map<String,String> failMap = Maps.newHashMap();

        String templatePath = "template.ftl";
        Map<String,Object> paramMap = Maps.newHashMap();
        List<ArticleCategory> categories = articleCategoryDAO.findActiveCatOrderBySequence();

        // reassemble all article html and list html
        List<Article> articleList = articleDAO.findAll();

        Map<String,Article> atjIdArtMap = Maps.newHashMap();
        for(Article art :articleList){
            String artFileName = art.getArtFileName();
            String str = artFileName.substring(artFileName.lastIndexOf("/") + 1);
            atjIdArtMap.put(str.substring(0,str.indexOf(".")), art);
        }


        for(Map.Entry<String, Article> entry : atjIdArtMap.entrySet()){
            String atjId = entry.getKey();
            Article article = entry.getValue();

            paramMap.clear();

            paramMap.put("innnercss", "");
            paramMap.put("outercss", "");
            paramMap.put("postDate", article.getGenerateTime());
            paramMap.put("from", article.getOriginUrl());
            paramMap.put("type", "article");
            paramMap.put("artCateList", categories);
            paramMap.put("article", article);

            String destFileStr = TaskConstants.GENERATED_FLODER + CommonConsts.SLASH + atjId + ".html";
            String contentFloderStr = TaskConstants.ART_ELE_FLODER + CommonConsts.SLASH + atjId;
            File file = new File(contentFloderStr);
            if(file.exists() && file.isDirectory()){
                File[] files = file.listFiles();
                for(File f : files){
                    if("title".equals(f.getName()) || "content".equals(f.getName())){
                        try{
                            paramMap.put(f.getName(), FileUtils.readFileToString(f,CommonConsts.UTF8));
                        }catch (IOException e){
                            failMap.put(article.getName(),e.getMessage());
                            continue;
                        }
                    }
                }

                try {
                    freemarkerHelper.assemble(paramMap,templatePath,destFileStr);

                    // cp new html to deploy floder
                    File fromFile = new File(destFileStr);
                    File toFile = new File(TaskConstants.DEPLOY_FLODER + CommonConsts.SLASH + article.getArticleCategory().getPathName() + CommonConsts.SLASH + atjId + ".html");
                    FileUtils.copyFile(fromFile,toFile);

                    successList.add(article.getName());
                } catch (Exception e){
                    failMap.put(article.getName(),e.getMessage());
                    continue;
                }

            }else{

            }
        }
        System.err.println("Article Reassemble Finished");

        //category list

        paramMap.clear();

        List<ArticleCategory> allCatList = articleCategoryDAO.findAll();
        String catOriPath = null;
        File oriCatFile= null;
        File toCatFile = null;
        for(ArticleCategory cat : allCatList){
            try{
                catOriPath = TaskConstants.GENERATED_FLODER + CommonConsts.SLASH + cat.getPathName() + CommonConsts.SLASH + "list.html";

                paramMap.put("type", "list");

                List<Article> list = articleDAO.findByCategory(cat.getName());
                paramMap.put("articleList", list);
                paramMap.put("artCateList", categories);
                paramMap.put("title", cat.getName());

                freemarkerHelper.assemble(paramMap, templatePath, catOriPath);
                oriCatFile = FileUtils.getFile(catOriPath);

                toCatFile = FileUtils.getFile(TaskConstants.DEPLOY_FLODER + CommonConsts.SLASH + cat.getPathName() + CommonConsts.SLASH + "list.html");
                FileUtils.copyFile(oriCatFile, toCatFile);

                successList.add(cat.getName());

            }catch (Exception e){
                e.printStackTrace();
                failMap.put(cat.getName(),e.getMessage());
                continue;
            }
        }

        // notify web
        System.err.println("List Reassemble Finished");


        System.out.println("Success reassembled and redeployed pages: " + successList.size());
        for(String str : successList){
            System.out.println("  " + str);
        }
        System.out.println("=====================");
        System.out.println("Fail reassembled and redeployed pages: " + failMap.size());
        for(String str : failMap.keySet()){
            System.out.println("  " + str + " " + failMap.get(str));
        }
    }

    public static void main(String[] args){
        String str = "/Users/tomchn/www/sss/dd/ss/wewrwrwerwerwew";

        System.out.println(str.substring(str.lastIndexOf("/") + 1));
    }

}
