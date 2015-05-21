package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Created by tomchen on 15-4-4.
 */
@Component
public class NotifyService {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(NotifyService.class);

    public void notifyWeb(Message<?> msg){
        ArticleTaskJob atj = (ArticleTaskJob) msg.getPayload();

        if(atj!=null){
            System.err.println("notifyWeb" + atj.getName());
        }else {
            System.err.println("");
        }
    }
}
