package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.dao.ArticleTaskJobDAO;
import com.cc.lmsfc.common.model.article.Article;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.common.util.HttpClientUtil;
import com.cc.lmsfc.task.exception.TaskJobException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by tomchen on 15-3-16.
 */
@Component
public class ArtTaskJobService {

    private static Logger logger = Logger.getLogger(ArtTaskJobService.class);

    @Autowired
    private ArticleTaskJobDAO articleTaskJobDAO;


    public ArticleTaskJob messageHandler(Message<?> msg){
        logger.info("get ArticleTaskJob from jms.");
        ArticleTaskJob atj = (ArticleTaskJob)msg.getPayload();
        return atj;
    }

    public ArticleTaskJob validate(ArticleTaskJob atj){

        //TODO validate ArticleTaskJob
//        String batchName = atj.getBatchArticleTaskJob() !=null ? atj.getBatchArticleTaskJob().getName() : "";
//        System.err.println("ArticleTaskJob:" + atj.getId() + " - " + atj.getName() + " - " + batchName);
//        throw new RuntimeException("test runtimeEpt");

        return atj;
    }

    public ArticleTaskJob download(ArticleTaskJob atj){
        logger.info("Download article page from: " + atj.getUrl());

        byte[] bytes  = null;
        try {
            bytes = HttpClientUtil.httpGet(atj.getUrl());
            if(ArrayUtils.isEmpty(bytes)){
                throw new TaskJobException("Exception occurs when downliad article page, get empty page. ");
            }

            atj.getTempMap().put("respBytes",bytes);
            System.err.println(new String((byte[]) atj.getTempMap().get("respBytes"),"UTF-8"));
            return atj;
        } catch (Exception e) {
            //TODO
            throw new TaskJobException(e.getMessage(),e);
        }


    }


}