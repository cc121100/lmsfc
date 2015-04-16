package com.cc.lmsfc.common.service.impl;

import com.cc.lmsfc.common.constant.CommonConsts;
import com.cc.lmsfc.common.dao.ArticleTaskJobDAO;
import com.cc.lmsfc.common.dao.FilterRuleDAO;
import com.cc.lmsfc.common.model.task.ArticleTaskJob;
import com.cc.lmsfc.common.service.ArticleTaskJobService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by tomchen on 15-3-30.
 */
@Service
public class ArticleTaskJobServiceImpl extends BaseServiceImpl<ArticleTaskJob,ArticleTaskJobDAO,String>
                                        implements ArticleTaskJobService{

    @Autowired
    private ArticleTaskJobDAO articleTaskJobDAO;

    @Autowired
    private FilterRuleDAO filterRuleDAO;

    @Override
    protected ArticleTaskJobDAO getRepository() {
        return articleTaskJobDAO;
    }

    @Override
    public List<ArticleTaskJob> findAll() {
        return super.findAll();
    }


    @Override
    public void update(ArticleTaskJob articleTaskJob) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        retriveObj(articleTaskJob);
        super.update(articleTaskJob);
    }

    @Override
    public void save(ArticleTaskJob articleTaskJob) {
        retriveObj(articleTaskJob);
        if(articleTaskJob.getState() == null){
            articleTaskJob.setState(0);
        }
        super.save(articleTaskJob);
    }

    @Override
    public void updateState(String id, boolean isSuccess) {
        ArticleTaskJob atj = getRepository().findOne(id);
        if(atj.getState() == 0){
            atj.setState(111);
        }else {
            atj.setState(CommonConsts.updateArtState(atj.getState(),isSuccess,atj.getIsWhole()));
        }
        getRepository().saveAndFlush(atj);

    }

    @Override
    public ArticleTaskJob saveAndReturn(ArticleTaskJob atj) {
        retriveObj(atj);
        if(atj.getState() == null){
            atj.setState(0);
        }
        return articleTaskJobDAO.saveAndFlush(atj);
    }

    @Override
    public ArticleTaskJob getAtjAndLog(String id) {
        ArticleTaskJob atj = articleTaskJobDAO.findOne(id);
        atj.getTaskJobRunLogs().size();
        return atj;
    }

    private void retriveObj(ArticleTaskJob articleTaskJob) {
        String filterRuleId = articleTaskJob.getFilterRule().getId();
        if(StringUtils.isNotEmpty(filterRuleId)){
            articleTaskJob.setFilterRule(filterRuleDAO.findOne(filterRuleId));
        }else{
            articleTaskJob.setFilterRule(null);
        }
    }
}
