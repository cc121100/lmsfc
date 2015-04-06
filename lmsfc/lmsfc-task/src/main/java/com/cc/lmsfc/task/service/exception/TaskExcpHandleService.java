package com.cc.lmsfc.task.service.exception;

import com.cc.lmsfc.common.dao.ArtTaskJobRunLogDAO;
import com.cc.lmsfc.common.dao.ArticleTaskJobDAO;
import com.cc.lmsfc.common.model.task.ArtTaskJobRunLog;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.common.model.task.TaskJob;
import com.cc.lmsfc.task.exception.*;
import com.cc.lmsfc.task.helper.ArtTaskJobHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tomchen on 15-4-3.
 */
@Component
public class TaskExcpHandleService {

    private Logger logger = Logger.getLogger(TaskExcpHandleService.class);

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
        //TODO handler exception if fail task job
        try{
            logger.info("Handle Task Exception.");
            RuntimeException e = (RuntimeException)msg.getPayload();

            if(e instanceof ArtTaskJobException){
                ArticleTaskJob atj = ((ArtTaskJobException) e).getTaskJob();
                artTaskJobHelper.updateAtjState(atj,atjDAO,false);

                ArtTaskJobRunLog artLog = new ArtTaskJobRunLog();
                artLog.setState(1);
                artLog.setArticleTaskJob(atj);
                artLog.setArtTaskStep(atj.getState());
                String message = e.getMessage();
                if(message.length() > 100){
                    message = message.substring(0,99);
                }
                artLog.setDescription(message);
                artTaskJobHelper.updateAtjLog(null,atjLogDAO,atjDAO);

            }else {

            }
        }catch (Exception ex){
            logger.error("Error occurs when handler task exception." + ex);
        }
    }
}
