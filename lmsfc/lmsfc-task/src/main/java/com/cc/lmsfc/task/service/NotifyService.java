package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by tomchen on 15-4-4.
 */
@Component
public class NotifyService {

    Logger logger = Logger.getLogger(NotifyService.class);

    public void notifyWeb(ArticleTaskJob atj){

        System.err.println("notifyWeb" + atj.getName());
    }
}
