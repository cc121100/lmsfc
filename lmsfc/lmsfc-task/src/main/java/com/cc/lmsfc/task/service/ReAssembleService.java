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
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-4-23.
 */
@Service
public class ReAssembleService {

    private Logger logger = Logger.getLogger(ReAssembleService.class);

    @Autowired
    private FreemarkerHelper freemarkerHelper;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    public Message reassemble(Message<?>  msg){

        List<String> successList = Lists.newArrayList();
        Map<String,String> failMap = Maps.newHashMap();

        String templatePath = "template.ftl";
        Map<String,Object> paramMap = Maps.newHashMap();
        List<ArticleCategory> categories = articleCategoryDAO.findActiveCatOrderBySequence();

        //category list
        List<ArticleCategory> allCatList = articleCategoryDAO.findActiveCatOrderBySequence();
        String catOriPath = null;
        String catToPath = null;
        String catOriPathList = null;
        File oriCatFile= null;
        File toCatFile = null;
        for(ArticleCategory cat : allCatList){
            try{
                catOriPath = TaskConstants.GENERATED_FLODER + CommonConsts.SLASH + cat.getPathName();
                File f = new File(catOriPath);
                if(!f.exists()){
                    f.mkdirs();
                }

                catOriPathList = catOriPath + CommonConsts.SLASH + "list.html";


                paramMap.put("type", "list");

                List<Article> list = articleDAO.findByCategory(cat.getName());
                if(list.size() > 10){
                    list = list.subList(0,10);
                }
                paramMap.put("articleList", list);
                paramMap.put("artCateList", categories);
                paramMap.put("title", cat.getName());

                freemarkerHelper.assemble(paramMap, templatePath, catOriPathList);
                oriCatFile = FileUtils.getFile(catOriPathList);

                catToPath = TaskConstants.DEPLOY_FLODER + CommonConsts.SLASH + cat.getPathName();
                if(!new File(catToPath).exists()){
                    new File(catToPath).mkdirs();
                }

                toCatFile = FileUtils.getFile(catToPath + CommonConsts.SLASH + "list.html");
                FileUtils.copyFile(oriCatFile, toCatFile);

                successList.add(cat.getName());

            }catch (Exception e){
                logger.error("Error occurs when assemble category list page: " +e.getMessage());
                failMap.put(cat.getName(),e.getMessage());
                continue;
            }
        }

        paramMap.clear();


        // reassemble all article html
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

        //cp css/img
        URL url = this.getClass().getClassLoader().getResource("deploy_init_content");
        String deployInitFloder = url.getFile();
        try{
            FileUtils.copyDirectory(new File(deployInitFloder),new File(TaskConstants.DEPLOY_FLODER));
        }catch (Exception e){
            logger.error("Error occurs when reassemble :" + e.getMessage());
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

        return msg;
    }

    public static void main(String[] args){
        String str = "/Users/tomchn/www/sss/dd/ss/wewrwrwerwerwew";

        System.out.println(str.substring(str.lastIndexOf("/") + 1));

        URL url = ReAssembleService.class.getClassLoader().getResource("deploy_init_content");
        System.err.println(url.getFile());

        try {
            FileUtils.copyDirectory(new File(url.getFile()),new File("/Users/tomchen/lmsfc-task/sss"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
