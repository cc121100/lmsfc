package com.cc.lmsfc.task.helper;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.dao.ArtTaskJobRunLogDAO;
import com.cc.lmsfc.common.dao.ArticleTaskJobDAO;
import com.cc.lmsfc.common.model.task.ArtTaskJobRunLog;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Map;

/**
 * Created by tomchen on 15-4-3.
 */
@Component
public class ArtTaskJobHelper {

    @Autowired
    @Qualifier(value = "notifyJmsTemplate")
    private JmsTemplate jmsTemplate;

    public void updateAtjState(ArticleTaskJob atj, ArticleTaskJobDAO atjDAO, boolean isSuccess){
        atj.setState(CommonConsts.updateArtState(atj.getState(),isSuccess,atj.getIsWhole()));
        atjDAO.updateArticleState(atj.getId(),atj.getState());
    }

    public void updateAtjLog(ArtTaskJobRunLog artTaskJobRunLog,ArtTaskJobRunLogDAO logDAO, ArticleTaskJobDAO artTaskDAO){

        String artTaskJobId = artTaskJobRunLog.getArticleTaskJob().getId();
        if(StringUtils.isEmpty(artTaskJobId)){
            artTaskJobRunLog.setArticleTaskJob(null);
        }else {
            ArticleTaskJob atj = artTaskDAO.findOne(artTaskJobId);
            if (atj == null){
                artTaskJobRunLog.setArticleTaskJob(null);
            }else {
                artTaskJobRunLog.setArticleTaskJob(atj);
            }
        }

        logDAO.saveAndFlush(artTaskJobRunLog);

    }

    public void notifyWeb(final Map<String,Object> map){
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage((java.io.Serializable) map);
            }
        });
    }



}
