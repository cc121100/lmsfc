package com.cc.lmsfc.task.service;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.dao.ArticleElementDAO;
import com.cc.lmsfc.common.model.article.ArticleElement;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.task.constant.TaskConstants;
import com.cc.lmsfc.task.exception.GenerateArtEleException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by tomchen on 15-3-18.
 */
@Component
public class ArtElementService {

    private Logger logger = LoggerFactory.getLogger(ArtElementService.class);


    @Autowired
    private ArticleElementDAO articleElementDAO;

    @Transactional
    public ArticleTaskJob generate(ArticleTaskJob atj) {

        logger.info("Generate elements for atj " + atj.getName());

        try{
            // 1 generate article element
            ArticleElement artEle = null;

            List<ArticleElement> list = articleElementDAO.findByArticleTaskJobId(atj.getId());
            if(CollectionUtils.isNotEmpty(list)){
                artEle = list.get(0);
            }

            if(artEle == null){
                artEle = new ArticleElement();

                artEle.setArticleTaskJob(atj);
                artEle.setName(CommonConsts.ELE_PREFIX + atj.getName());
                artEle.setState(0);
                artEle.setFiles("test");

                artEle.setFileLocation(TaskConstants.ART_ELE_FLODER + CommonConsts.SLASH + artEle.getId());
                articleElementDAO.saveAndFlush(artEle);
//                artEle.setFileLocation(TaskConstants.ART_ELE_FLODER + CommonConsts.SLASH + artEle.getId());
            }

            atj.setArticleElement(artEle);

            logger.info("Store element files in " + artEle.getFileLocation());
            // 2 store related files in disk
            for (Map.Entry<String,Object> entry : atj.getTempMap().entrySet()){
                String fileName = entry.getKey();
                String fileContent = (String) entry.getValue();
                FileUtils.write(new File(artEle.getFileLocation() + CommonConsts.SLASH + fileName), fileContent, CommonConsts.UTF8);

            }

            return atj;

        }catch (Exception e){
            logger.error("Error occurs when generate article element for atj:" + atj.getName() + ", " + e.getMessage());
            throw new GenerateArtEleException(e,atj);
        }

    }
}
