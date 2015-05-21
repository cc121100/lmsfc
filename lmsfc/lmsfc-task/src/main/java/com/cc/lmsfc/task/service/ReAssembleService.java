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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import sun.misc.Launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by tomchen on 15-4-23.
 */
@Service
public class ReAssembleService {

    private Logger logger = LoggerFactory.getLogger(ReAssembleService.class);


    @Autowired
    private FreemarkerHelper freemarkerHelper;

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private ArticleCategoryDAO articleCategoryDAO;

    public Message reassemble(Message<?>  msg){

        logger.info("Ressemble and deploy all articles.");

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
//        System.err.println("Article Reassemble Finished");
        logger.info("Article Reassemble Finished");

        try{
            //cp css/img
//            InputStream in = this.getClass().getClassLoader().getResourceAsStream("deploy_init_content");

            final String path = TaskConstants.DEPLOY_INIT_CONTENT_NAME;
            final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

            if(jarFile.isFile()) {  // Run with JAR file
//                System.out.println("jar");
                logger.info("Run with jar file");
                final JarFile jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
                while(entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    final String name = jarEntry.getName();
                    if (name.startsWith(path + "/")) { //filter according to the path
//                        System.out.println(name);
                        if(jarEntry.isDirectory()){
                            // mkdirs
                            String prefix = "";
                            prefix = name.substring(path.length());
//                            System.out.println("prefix:" + prefix);// suffix:/img/1.jpg
                            File d = new File(TaskConstants.DEPLOY_FLODER + CommonConsts.SLASH + prefix);
                            if(!d.exists()){
                                d.mkdirs();
                            }
                        }else {
                            String suffix = "";
                            suffix = name.substring(path.length());
//                            System.out.println("suffix:" + suffix);// suffix:/img/1.jpg

                            InputStream in = this.getClass().getResourceAsStream(CommonConsts.SLASH + name);
                            if(in ==null){
                                throw new Exception("Cant get resource " + name );
                            }
                            IOUtils.copy(in,new FileOutputStream(TaskConstants.DEPLOY_FLODER + suffix));
                        }
//                        FileUtils.copyFileToDirectory(new File(name),new File(TaskConstants.DEPLOY_FLODER));
                    }
                }
                jar.close();
            } else { // Run with IDE
//                System.out.println("ide");
                logger.info("Run with ide");
                final URL url = Launcher.class.getResource("/" + path);
                if (url != null) {
                    try {
                        final File apps = new File(url.toURI());
                        for (File app : apps.listFiles()) {
                            System.out.println(app);
                        }
                    } catch (URISyntaxException ex) {
                        // never happens
                    }
                }
            }

//            Enumeration<URL> resources = this.getClass().getClassLoader().getResources("deploy_init_content");
//            if(resources.hasMoreElements()){
//                System.out.println(resources.nextElement().getFile());
//                FileUtils.copyDirectory(new File(resources.nextElement().getFile()),new File(TaskConstants.DEPLOY_FLODER));
//            }

//            String deployInitFloder = url.getFile();
        }catch (Exception e){
            logger.error("Error occurs when reassemble :" + e.getMessage());
        }


        // notify web
        System.err.println("List Reassemble Finished");
        logger.info("List Reassemble Finished");


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

    public static void main(String[] args) throws IOException {
//        String str = "/Users/tomchn/www/sss/dd/ss/wewrwrwerwerwew";
//
//        System.out.println(str.substring(str.lastIndexOf("/") + 1));
//
//        URL url = ReAssembleService.class.getClassLoader().getResource("deploy_init_content");
//        System.err.println(url.getFile());
//
//        try {
//            FileUtils.copyDirectory(new File(url.getFile()),new File("/Users/tomchen/lmsfc-task/sss"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Enumeration<URL> resources = ReAssembleService.class.getClassLoader().getResources("deploy_init_content");
        System.out.println("exists = " + resources.hasMoreElements());

        File f = new File("file:/Users/tomchen/work/lmsfc-workspace/lmsfc/lmsfc-task/target/lmsfc-task.jar!/deploy_init_content");
        System.out.println(f.getPath());
    }

}
