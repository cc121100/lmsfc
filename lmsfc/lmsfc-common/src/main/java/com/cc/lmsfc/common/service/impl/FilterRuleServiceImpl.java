package com.cc.lmsfc.common.service.impl;

import com.cc.lmsfc.common.dao.FilterRuleDAO;
import com.cc.lmsfc.common.model.filter.FilterRule;
import com.cc.lmsfc.common.service.FilterRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tomchen on 15-3-28.
 */
@Service
public class FilterRuleServiceImpl extends BaseServiceImpl<FilterRule,FilterRuleDAO,String>
                                    implements FilterRuleService {

    @Autowired
    private FilterRuleDAO filterRuleDAO;

    @Override
    protected FilterRuleDAO getRepository() {
        return filterRuleDAO;
    }
}
