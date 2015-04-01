package com.cc.lmsfc.common.service.impl;

import com.cc.lmsfc.common.dao.FilterDAO;
import com.cc.lmsfc.common.dao.FilterDetailDAO;
import com.cc.lmsfc.common.dao.FilterRuleDAO;
import com.cc.lmsfc.common.model.filter.FilterDetail;
import com.cc.lmsfc.common.service.FilterDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by tomchen on 15-3-29.
 */
@Service
public class FilterDetailServiceImpl extends BaseServiceImpl<FilterDetail,FilterDetailDAO,String> implements FilterDetailService {

    @Autowired
    private FilterDetailDAO filterDetailDAO;

    @Autowired
    private FilterDAO filterDAO;

    @Autowired
    private FilterRuleDAO filterRuleDAO;

    @Override
    protected FilterDetailDAO getRepository() {
        return filterDetailDAO;
    }


    @Override
    public void save(FilterDetail filterDetail) {
        retriveObj(filterDetail);

        filterDetailDAO.saveAndFlush(filterDetail);

    }

    private void retriveObj(FilterDetail filterDetail) {
        String filterId = filterDetail.getFilter().getId();
        if(StringUtils.isNotEmpty(filterId)){
            filterDetail.setFilter(filterDAO.findOne(filterId));
        }else{
            filterDetail.setFilter(null);
        }

        String filterRuleId = filterDetail.getFilterRule().getId();
        if(StringUtils.isNotEmpty(filterRuleId)){
            filterDetail.setFilterRule(filterRuleDAO.findOne(filterRuleId));
        }else{
            filterDetail.setFilterRule(null);
        }

        String parentNodeId = filterDetail.getParentNode().getId();
        if(StringUtils.isNotEmpty(parentNodeId)){
            filterDetail.setParentNode(filterDetailDAO.findOne(parentNodeId));
        }else{
            filterDetail.setParentNode(null);
        }
    }

    @Override
    public void update(FilterDetail filterDetail) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        retriveObj(filterDetail);
        super.update(filterDetail);
    }
}
