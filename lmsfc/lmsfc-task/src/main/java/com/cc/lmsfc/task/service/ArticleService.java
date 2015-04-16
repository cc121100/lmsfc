package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.dao.ArticleCategoryDAO;
import com.cc.lmsfc.common.dao.ArticleDAO;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.article.ArticleCategory;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.task.constant.TaskConstants;
import com.cc.lmsfc.task.exception.AssembleArtException;
import com.cc.lmsfc.task.exception.DeployArtException;
import freemarker.core.Configurable;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

/**
 * Created by tomchen on 15-3-19.
 */
@Component
public class ArticleService {

    private Logger logger = Logger.getLogger(ArticleService.class);

    @Autowired
    private Configuration configuration;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    @Transactional
    public ArticleTaskJob assemble(ArticleTaskJob atj){

        // 1 article page
        try {
            List<ArticleCategory> articleCategorys = articleCategoryDAO.findActiveCatOrderBySequence();

//            ArticleCategory articleCategory = articleCategorys.get(0);
            String artFileName = atj.getArticleElement().getId() + ".html";

            Article article = new Article();
            article.setName(atj.getArticleElement().getName().replaceFirst(CommonConsts.ELE_PREFIX, ""));
            article.setGenerateTime(new Date());
            article.setState(0);
            article.setArtFileName(artFileName);
            article.setDescription(article.getName());

            if (CollectionUtils.isNotEmpty(articleCategorys)) {
                for (ArticleCategory artC : articleCategorys) {
                    if (artC.getName().equals(atj.getTargetCategory())) {
                        article.setArticleCategory(artC);//
                        break;
                    }
                }
            } else {
                article.setArticleCategory(null);//

            }
            article.setArticleElement(atj.getArticleElement());

            articleDAO.saveAndFlush(article);

            atj.getArticleElement().setArticle(article);

            if (MapUtils.isEmpty(atj.getTempMap())) {
                File file = new File(atj.getArticleElement().getFileLocation());
                File[] fileList = file.listFiles();
                if (ArrayUtils.isEmpty(fileList)) {
                    logger.error("No 4 Art ele files under :" + atj.getArticleElement().getFileLocation());
                    throw new AssembleArtException("No 4 Art ele files under :" + atj.getArticleElement().getFileLocation(), atj);
                }
                for (File f : fileList) {
                    atj.getTempMap().put(f.getName(), FileUtils.readFileToString(f, "UTF-8"));
                }

            }

            Map<String, Object> map = new HashMap<>();
            map.put("title", atj.getTempMap().get("title"));
            map.put("content", atj.getTempMap().get("content"));
            map.put("innnercss", "");
            map.put("outercss", "");
//            map.put("innnercss",atj.getTempMap().get("innnercss"));
//            map.put("outercss",atj.getTempMap().get("outercss"));
            map.put("postDate", article.getGenerateTime());
            map.put("from", atj.getUrl());
            map.put("type", "article");
            map.put("artCateList", articleCategorys);
            map.put("article", article);


            String cateGeneratedFloder = TaskConstants.GENERATED_FLODER + CommonConsts.SLASH + article.getArticleCategory().getPathName();
            File f = new File(cateGeneratedFloder);
            if (!f.exists()) {
                f.mkdirs();
            }

            String destFileName = cateGeneratedFloder + CommonConsts.SLASH + artFileName;


            Template template = configuration.getTemplate("template.ftl");
            generateFile(template, map, destFileName);

            //regex all html tag and only save 100 chars.
            String contentStr = atj.getTempMap().get("content").toString().
                    replaceAll(CommonConsts.REG_HTML_TAG, "").
                    replaceAll(CommonConsts.REG_HTML_CHAR, "").trim();
            if (contentStr.length() > 200) {
                contentStr = contentStr.substring(0, 199) + "......";
            }

            article.setDescription(contentStr);
            article.setArtFileName(destFileName);
            articleDAO.saveAndFlush(article);

        }catch (Exception e){
            logger.error("Error occurs when assemble article.");
            throw new AssembleArtException(e,atj);
        }

        return atj;
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

    public ArticleTaskJob deploy(ArticleTaskJob atj){
        try{
            // 1. update article state
            logger.info("Deploy");
//            int i = 1/0;

            //  generate and move categpry list.html
            Map<String,Object> map = new HashMap<>();
            map.put("type","list");

            //get all articles of this category
            List<ArticleCategory> articleCategorys = articleCategoryDAO.findActiveCatOrderBySequence();
            Article article = atj.getArticleElement().getArticle();
            ArticleCategory artCate = article.getArticleCategory();

            List<Article> articleList = articleDAO.findByCategory(artCate.getName());
            map.put("articleList",articleList);
            map.put("artCateList",articleCategorys);
            map.put("title",artCate.getName());

            String cateGeneratedFloder = TaskConstants.GENERATED_FLODER + CommonConsts.SLASH + artCate.getPathName();
            File f = new File(cateGeneratedFloder);
            if(!f.exists()){
                f.mkdirs();
            }

            String destFileName = cateGeneratedFloder + CommonConsts.SLASH +  "list.html";

            Template template = configuration.getTemplate("template.ftl");
            generateFile(template,map,destFileName);

            //todo move categpry list.html
            // todo 2. move article.html to deployed floder
            String deployFloder = TaskConstants.DEPLOY_FLODER + CommonConsts.SLASH + article.getArticleCategory().getPathName();
            File destFloder = new File(deployFloder);
            if(!destFloder.exists()){
                destFloder.mkdirs();
            }

            File oldArtFile = new File(deployFloder + CommonConsts.SLASH + atj.getArticleElement().getId() + ".html");
            if(oldArtFile.exists()){
                oldArtFile.delete();
            }

            File oldListFile = new File(deployFloder + CommonConsts.SLASH + "list.html");
            if(oldListFile.exists()){
                oldListFile.delete();
            }

            FileUtils.copyFileToDirectory(new File(destFileName), destFloder, false);
            FileUtils.copyFileToDirectory(new File(article.getArtFileName()), destFloder, false);

        }catch (Exception e){
            logger.error("Error occurs when deploy article");
            throw new DeployArtException(e,atj);
        }
        return atj;
    }


}
