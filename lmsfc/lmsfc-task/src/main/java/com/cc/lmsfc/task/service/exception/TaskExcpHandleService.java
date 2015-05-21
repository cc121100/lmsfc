package com.cc.lmsfc.task.service.exception;

import com.cc.lmsfc.common.dao.ArtTaskJobRunLogDAO;
import com.cc.lmsfc.common.dao.ArticleTaskJobDAO;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.task.exception.*;
import com.cc.lmsfc.task.helper.ArtTaskJobHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tomchen on 15-4-3.
 */
@Component
public class TaskExcpHandleService {

    private Logger logger = LoggerFactory.getLogger(TaskExcpHandleService.class);

    @Autowired
    private ArticleTaskJobDAO atjDAO;

    @Autowired
    private ArtTaskJobRunLogDAO atjLogDAO;

    @Autowired
    private ArtTaskJobHelper artTaskJobHelper;

    // 1 update state
    // 2 update task run log
    // 3 notify

    @Transactional
    public void handler(Message<?> msg){
        try{
            logger.info("Handle Task Exception.");
            Throwable e = ((Throwable)msg.getPayload()).getCause().getCause();

            if(e instanceof ArtTaskJobException){
                ArticleTaskJob atj = ((ArtTaskJobException) e).getTaskJob();

                artTaskJobHelper.updateAtjLog(atj,atjLogDAO,atjDAO,false,e.getMessage());
                artTaskJobHelper.updateAtjState(atj,atjDAO,false);

            }else {

            }
        }catch (Exception ex){
            logger.error("Error occurs when handler task exception." + ex);
        }
    }
}
