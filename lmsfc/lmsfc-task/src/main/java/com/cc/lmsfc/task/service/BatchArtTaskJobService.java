package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.common.model.task.BatchArticleTaskJob;
import org.apache.log4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tomchen on 15-3-16.
 */

@Component
public class BatchArtTaskJobService {

    private static Logger logger = Logger.getLogger(BatchArtTaskJobService.class);

    public List<ArticleTaskJob> messageHandler(Message<?> msg){
        logger.info("messageHandler, get batchArtTask from jms.");

        BatchArticleTaskJob batj = (BatchArticleTaskJob)msg.getPayload();

        return batj.getArticleTaskJobs();

    }
}
