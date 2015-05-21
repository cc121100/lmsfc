package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.dao.ArtTaskJobRunLogDAO;
import com.cc.lmsfc.common.dao.ArticleTaskJobDAO;
import com.cc.lmsfc.common.dao.FilterDetailDAO;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.common.util.HttpClientUtil;
import com.cc.lmsfc.task.constant.TaskConstants;
import com.cc.lmsfc.task.exception.ArtTaskJobException;
import com.cc.lmsfc.task.exception.GetArticleException;
import com.cc.lmsfc.task.helper.ArtTaskJobHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Map;

/**
 * Created by tomchen on 15-3-16.
 */
@Component
public class ArtTaskJobService {

    private Logger logger = LoggerFactory.getLogger(ArtTaskJobService.class);

    @Autowired
    private ArticleTaskJobDAO articleTaskJobDAO;

    @Autowired
    private FilterDetailDAO filterDetailDAO;

    @Autowired
    private ArtTaskJobRunLogDAO logDAO;

    @Autowired
    private ArtTaskJobHelper artTaskJobHelper;

    public ArticleTaskJob messageHandler(Message<?> msg){
        logger.info("Get ArticleTaskJob from redis.");
//        ArticleTaskJob atj = (ArticleTaskJob)msg.getPayload();
        Map<String,Object> map = (Map<String, Object>) msg.getPayload();
        String id = (String) map.get("id");
        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("TaskJob id is empty.");
        }

        //load ArticleTaskJob by id
        ArticleTaskJob art = articleTaskJobDAO.findOne(id);
        if(art == null){
            throw new RuntimeException("Cant find artTaskJob with id: " + id,null);
        }
        art.getBatchArticleTaskJob();
        if(art.getArticleElement() != null){
            art.getArticleElement().getArticle();
        }
        art.getFilterRule().getId();
        art.getFilterRule().getFilterDetails().size();
        //get common inner/outer css filter details
        art.getFilterRule().getFilterDetails().addAll(filterDetailDAO.findCommonFilterDetais());

        return art;
    }

    public ArticleTaskJob validate(ArticleTaskJob atj){

        //TODO validate ArticleTaskJob
        logger.info("Validate article task with filter rule.");
//        String batchName = atj.getBatchArticleTaskJob() !=null ? atj.getBatchArticleTaskJob().getName() : "";
//        System.err.println("ArticleTaskJob:" + atj.getId() + " - " + atj.getName() + " - " + batchName);
//        throw new RuntimeException("test runtimeEpt");

        return atj;
    }

    public ArticleTaskJob download(ArticleTaskJob atj){
        logger.info("Download article page from: " + atj.getUrl());
        logger.info("Chcek if exisit temp file for this article page.");

        byte[] bytes  = null;
        String tmepFileStr = TaskConstants.ART_ELE_FLODER + CommonConsts.SLASH + "temp" +CommonConsts.SLASH + atj.getId() +".temp";
        File tempFile = new File(tmepFileStr);

        try{
            if(tempFile.exists()){
                logger.info("Exsit temp file: " + tmepFileStr);
                bytes = FileUtils.readFileToByteArray(tempFile);
            }else {
                logger.info("No exsit temp file, get article from url:" + atj.getUrl());
                bytes = HttpClientUtil.httpGet(atj.getUrl());
                if(ArrayUtils.isEmpty(bytes)){
                    throw new ArtTaskJobException("Exception occurs when downliad article page, get empty page. ",atj);
                }

                //todo store in tmp/id.tmp when first time download, after that retry can get content directly from tmp
                FileUtils.writeByteArrayToFile(tempFile,bytes);
            }

            atj.getTempMap().put("respBytes", bytes);
//            System.err.println(new String((byte[]) atj.getTempMap().get("respBytes"), "UTF-8"));

        }catch (Exception e){
            logger.error("Error occurs when download article:" + atj.getUrl() + ", e:" + e.getMessage());
            throw new GetArticleException(e,atj);
        }

        return atj;

    }

    @Transactional
    public ArticleTaskJob updateArtStateAndLog(ArticleTaskJob atj){
        try{
            //  add task run log
            artTaskJobHelper.updateAtjLog(atj,logDAO,articleTaskJobDAO,true,null);

            //  update atj's state
            artTaskJobHelper.updateAtjState(atj,articleTaskJobDAO,true);
        }catch (Exception e){
            logger.error("Error when updateArtStateAndLog:" + e.getMessage(),e);
        }
        return atj;
    }


}